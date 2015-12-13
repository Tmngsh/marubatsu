// SerialCommunication.cpp : アプリケーションのエントリ ポイントを定義します。
//

#include "stdafx.h"
#include "SerialCommunication.h"

#define MAX_LOADSTRING 100

// グローバル変数:
HINSTANCE hInst;								// 現在のインターフェイス
TCHAR szTitle[MAX_LOADSTRING];					// タイトル バーのテキスト
TCHAR szWindowClass[MAX_LOADSTRING];			// メイン ウィンドウ クラス名

HWND sendButton;		// 送信ボタンのハンドル
HWND sendCommand;		// 送信するコマンドのハンドル
HWND receiveCommand;	// 受信するコマンドのハンドル
HANDLE comPort;			// シリアル通信をするCOMポートのハンドル

// このコード モジュールに含まれる関数の宣言を転送します:
ATOM				MyRegisterClass(HINSTANCE hInstance);
BOOL				InitInstance(HINSTANCE, int);
LRESULT CALLBACK	WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK	About(HWND, UINT, WPARAM, LPARAM);

// 独自の関数宣言
bool createItems(HWND hwnd);
bool initCOMPort();

int APIENTRY _tWinMain(HINSTANCE hInstance,
                     HINSTANCE hPrevInstance,
                     LPTSTR    lpCmdLine,
                     int       nCmdShow)
{
	UNREFERENCED_PARAMETER(hPrevInstance);
	UNREFERENCED_PARAMETER(lpCmdLine);

 	// TODO: ここにコードを挿入してください。
	MSG msg;
	HACCEL hAccelTable;

	// グローバル文字列を初期化しています。
	LoadString(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
	LoadString(hInstance, IDC_SERIALCOMMUNICATION, szWindowClass, MAX_LOADSTRING);
	MyRegisterClass(hInstance);

	// アプリケーションの初期化を実行します:
	if (!InitInstance (hInstance, nCmdShow))
	{
		return FALSE;
	}

	hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_SERIALCOMMUNICATION));

	


	// メイン メッセージ ループ:
	while (GetMessage(&msg, NULL, 0, 0))
	{
		if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
		{
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}
	}

	return (int) msg.wParam;
}



//
//  関数: MyRegisterClass()
//
//  目的: ウィンドウ クラスを登録します。
//
//  コメント:
//
//    この関数および使い方は、'RegisterClassEx' 関数が追加された
//    Windows 95 より前の Win32 システムと互換させる場合にのみ必要です。
//    アプリケーションが、関連付けられた
//    正しい形式の小さいアイコンを取得できるようにするには、
//    この関数を呼び出してください。
//
ATOM MyRegisterClass(HINSTANCE hInstance)
{
	WNDCLASSEX wcex;

	wcex.cbSize = sizeof(WNDCLASSEX);

	wcex.style			= CS_HREDRAW | CS_VREDRAW;
	wcex.lpfnWndProc	= WndProc;
	wcex.cbClsExtra		= 0;
	wcex.cbWndExtra		= 0;
	wcex.hInstance		= hInstance;
	wcex.hIcon			= LoadIcon(hInstance, MAKEINTRESOURCE(IDI_SERIALCOMMUNICATION));
	wcex.hCursor		= LoadCursor(NULL, IDC_ARROW);
	wcex.hbrBackground	= (HBRUSH)(COLOR_WINDOW+1);
	wcex.lpszMenuName	= MAKEINTRESOURCE(IDC_SERIALCOMMUNICATION);
	wcex.lpszClassName	= szWindowClass;
	wcex.hIconSm		= LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

	return RegisterClassEx(&wcex);
}

//
//   関数: InitInstance(HINSTANCE, int)
//
//   目的: インスタンス ハンドルを保存して、メイン ウィンドウを作成します。
//
//   コメント:
//
//        この関数で、グローバル変数でインスタンス ハンドルを保存し、
//        メイン プログラム ウィンドウを作成および表示します。
//
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
   HWND hWnd;

   hInst = hInstance; // グローバル変数にインスタンス処理を格納します。

   hWnd = CreateWindow(szWindowClass, szTitle, WS_OVERLAPPEDWINDOW,
      CW_USEDEFAULT, 0, CW_USEDEFAULT, 0, NULL, NULL, hInstance, NULL);

   if (!hWnd)
   {
      return FALSE;
   }

   ShowWindow(hWnd, nCmdShow);
   UpdateWindow(hWnd);

   return TRUE;
}

//
//  関数: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  目的:  メイン ウィンドウのメッセージを処理します。
//
//  WM_COMMAND	- アプリケーション メニューの処理
//  WM_PAINT	- メイン ウィンドウの描画
//  WM_DESTROY	- 中止メッセージを表示して戻る
//
//
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	int wmId, wmEvent;
	PAINTSTRUCT ps;
	HDC hdc;

	switch (message)
	{
	case WM_CREATE:
		// ボタンとテキストボックスの作成
		if(!createItems(hWnd)) {
			PostQuitMessage(0);
		}
		break;
	case WM_COMMAND:
		wmId    = LOWORD(wParam);
		wmEvent = HIWORD(wParam);
		// 選択されたメニューの解析:
		switch (wmId)
		{
		case IDM_ABOUT:
			DialogBox(hInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hWnd, About);
			break;
		case IDM_EXIT:
			DestroyWindow(hWnd);
			break;
		case BUTTON_ID:
			switch(wmEvent) {
			case BN_CLICKED:
				MessageBox(NULL, TEXT("Clicked Button!!"), TEXT("デバッグ"), MB_OK);
				// COMPortを初期化する
				initCOMPort();

				// コマンドを送信する
				LPSTR sendData;		// Windowsのcharのポインタ
				DWORD NumOfByte;
				sendData = new char [10];
				sendData = "#########";
//				GetWindowText(sendCommand, sendData, 10);
				WriteFile(comPort, sendData, 10, &NumOfByte, NULL);
				// 受信バッファがたまるまで待つ
				Sleep(5000);

				// コマンドを受信する
				DWORD errors;
				COMSTAT comStat;
				ClearCommError(comPort, &errors, &comStat);
				int lengthOfReceived = comStat.cbInQue;	// 受信したメッセージ長を取得する
				if(comStat.cbInQue == 0) {
					CloseHandle(comPort);
					return 0;
				}

				char *receiveData = new char [lengthOfReceived];
				ReadFile(comPort, receiveData, lengthOfReceived, &NumOfByte, NULL);
				SetWindowText(receiveCommand, receiveData);

				// COMPortを閉じる
				CloseHandle(comPort);

			}
			break;
		default:
			return DefWindowProc(hWnd, message, wParam, lParam);
		}
		break;
	case WM_PAINT:
		hdc = BeginPaint(hWnd, &ps);
		// TODO: 描画コードをここに追加してください...
		EndPaint(hWnd, &ps);
		break;
	case WM_DESTROY:
		PostQuitMessage(0);
		break;
	default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}
	return 0;
}

// バージョン情報ボックスのメッセージ ハンドラーです。
INT_PTR CALLBACK About(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	UNREFERENCED_PARAMETER(lParam);
	switch (message)
	{
	case WM_INITDIALOG:
		return (INT_PTR)TRUE;

	case WM_COMMAND:
		if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
		{
			EndDialog(hDlg, LOWORD(wParam));
			return (INT_PTR)TRUE;
		}
		break;
	}
	return (INT_PTR)FALSE;
}

bool createItems(HWND hwnd){
	// ボタンを配置する
	sendButton = CreateWindow(
		TEXT("BUTTON"), TEXT("送信"),
		WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON,
		300, 100, 50, 50,
		hwnd, (HMENU)BUTTON_ID, hInst, NULL
		);
	if(sendButton == NULL){
		return false;
	}

	// 送信コマンド用のテクストボックスを作る
	sendCommand = CreateWindow(
		TEXT("EDIT"), TEXT(":GZ#"),
		WS_CHILD | WS_VISIBLE | WS_BORDER | ES_LEFT,
		100, 100, 200, 50,
		hwnd, NULL, hInst, NULL
		);

	if(sendCommand == NULL) {
		return false;
	}

	// 受信したコマンドを表示する
	receiveCommand = CreateWindow(
		TEXT("EDIT"), TEXT("receive"),
		WS_CHILD | WS_VISIBLE | WS_BORDER | WS_VSCROLL |
		ES_LEFT | ES_READONLY | ES_MULTILINE,
		400, 100, 300, 400,
		hwnd, NULL, hInst, NULL
		);

	if(receiveCommand == NULL) {
		return false;
	}

	return true;
}


// COMポートの初期化
bool initCOMPort() {
	// シリアルポートを開く
	comPort = CreateFile(
		TEXT("COM4"), GENERIC_READ | GENERIC_WRITE, 
		0, NULL, OPEN_EXISTING, 0, NULL);
	if(comPort == NULL) {
		return false;
	}

	DCB dcb;	// シリアルポートの構成情報が入る構造体
	GetCommState(comPort, &dcb);	// 現在の設定値を読み込み

	dcb.BaudRate = 2400;			// ボーレート
	dcb.ByteSize = 8;				// データ長
	dcb.Parity = NOPARITY;			// パリティ
	dcb.StopBits = ONESTOPBIT;		// ストップビット長
	dcb.fOutxCtsFlow = FALSE;		// 送信時CTSフロー
	dcb.fRtsControl = RTS_CONTROL_ENABLE;	// RTSフロー

	SetCommState(comPort, &dcb);	// 変更した設定値を書き込む

	return true;

}
