// SerialCommunication.cpp : �A�v���P�[�V�����̃G���g�� �|�C���g���`���܂��B
//

#include "stdafx.h"
#include "SerialCommunication.h"

#define MAX_LOADSTRING 100

// �O���[�o���ϐ�:
HINSTANCE hInst;								// ���݂̃C���^�[�t�F�C�X
TCHAR szTitle[MAX_LOADSTRING];					// �^�C�g�� �o�[�̃e�L�X�g
TCHAR szWindowClass[MAX_LOADSTRING];			// ���C�� �E�B���h�E �N���X��

HWND sendButton;		// ���M�{�^���̃n���h��
HWND sendCommand;		// ���M����R�}���h�̃n���h��
HWND receiveCommand;	// ��M����R�}���h�̃n���h��
HANDLE comPort;			// �V���A���ʐM������COM�|�[�g�̃n���h��

// ���̃R�[�h ���W���[���Ɋ܂܂��֐��̐錾��]�����܂�:
ATOM				MyRegisterClass(HINSTANCE hInstance);
BOOL				InitInstance(HINSTANCE, int);
LRESULT CALLBACK	WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK	About(HWND, UINT, WPARAM, LPARAM);

// �Ǝ��̊֐��錾
bool createItems(HWND hwnd);
bool initCOMPort();

int APIENTRY _tWinMain(HINSTANCE hInstance,
                     HINSTANCE hPrevInstance,
                     LPTSTR    lpCmdLine,
                     int       nCmdShow)
{
	UNREFERENCED_PARAMETER(hPrevInstance);
	UNREFERENCED_PARAMETER(lpCmdLine);

 	// TODO: �����ɃR�[�h��}�����Ă��������B
	MSG msg;
	HACCEL hAccelTable;

	// �O���[�o������������������Ă��܂��B
	LoadString(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
	LoadString(hInstance, IDC_SERIALCOMMUNICATION, szWindowClass, MAX_LOADSTRING);
	MyRegisterClass(hInstance);

	// �A�v���P�[�V�����̏����������s���܂�:
	if (!InitInstance (hInstance, nCmdShow))
	{
		return FALSE;
	}

	hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_SERIALCOMMUNICATION));

	


	// ���C�� ���b�Z�[�W ���[�v:
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
//  �֐�: MyRegisterClass()
//
//  �ړI: �E�B���h�E �N���X��o�^���܂��B
//
//  �R�����g:
//
//    ���̊֐�����юg�����́A'RegisterClassEx' �֐����ǉ����ꂽ
//    Windows 95 ���O�� Win32 �V�X�e���ƌ݊�������ꍇ�ɂ̂ݕK�v�ł��B
//    �A�v���P�[�V�������A�֘A�t����ꂽ
//    �������`���̏������A�C�R�����擾�ł���悤�ɂ���ɂ́A
//    ���̊֐����Ăяo���Ă��������B
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
//   �֐�: InitInstance(HINSTANCE, int)
//
//   �ړI: �C���X�^���X �n���h����ۑ����āA���C�� �E�B���h�E���쐬���܂��B
//
//   �R�����g:
//
//        ���̊֐��ŁA�O���[�o���ϐ��ŃC���X�^���X �n���h����ۑ����A
//        ���C�� �v���O���� �E�B���h�E���쐬����ѕ\�����܂��B
//
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
   HWND hWnd;

   hInst = hInstance; // �O���[�o���ϐ��ɃC���X�^���X�������i�[���܂��B

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
//  �֐�: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  �ړI:  ���C�� �E�B���h�E�̃��b�Z�[�W���������܂��B
//
//  WM_COMMAND	- �A�v���P�[�V���� ���j���[�̏���
//  WM_PAINT	- ���C�� �E�B���h�E�̕`��
//  WM_DESTROY	- ���~���b�Z�[�W��\�����Ė߂�
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
		// �{�^���ƃe�L�X�g�{�b�N�X�̍쐬
		if(!createItems(hWnd)) {
			PostQuitMessage(0);
		}
		break;
	case WM_COMMAND:
		wmId    = LOWORD(wParam);
		wmEvent = HIWORD(wParam);
		// �I�����ꂽ���j���[�̉��:
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
				MessageBox(NULL, TEXT("Clicked Button!!"), TEXT("�f�o�b�O"), MB_OK);
				// COMPort������������
				initCOMPort();

				// �R�}���h�𑗐M����
				LPSTR sendData;		// Windows��char�̃|�C���^
				DWORD NumOfByte;
				sendData = new char [10];
				sendData = "#########";
//				GetWindowText(sendCommand, sendData, 10);
				WriteFile(comPort, sendData, 10, &NumOfByte, NULL);
				// ��M�o�b�t�@�����܂�܂ő҂�
				Sleep(5000);

				// �R�}���h����M����
				DWORD errors;
				COMSTAT comStat;
				ClearCommError(comPort, &errors, &comStat);
				int lengthOfReceived = comStat.cbInQue;	// ��M�������b�Z�[�W�����擾����
				if(comStat.cbInQue == 0) {
					CloseHandle(comPort);
					return 0;
				}

				char *receiveData = new char [lengthOfReceived];
				ReadFile(comPort, receiveData, lengthOfReceived, &NumOfByte, NULL);
				SetWindowText(receiveCommand, receiveData);

				// COMPort�����
				CloseHandle(comPort);

			}
			break;
		default:
			return DefWindowProc(hWnd, message, wParam, lParam);
		}
		break;
	case WM_PAINT:
		hdc = BeginPaint(hWnd, &ps);
		// TODO: �`��R�[�h�������ɒǉ����Ă�������...
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

// �o�[�W�������{�b�N�X�̃��b�Z�[�W �n���h���[�ł��B
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
	// �{�^����z�u����
	sendButton = CreateWindow(
		TEXT("BUTTON"), TEXT("���M"),
		WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON,
		300, 100, 50, 50,
		hwnd, (HMENU)BUTTON_ID, hInst, NULL
		);
	if(sendButton == NULL){
		return false;
	}

	// ���M�R�}���h�p�̃e�N�X�g�{�b�N�X�����
	sendCommand = CreateWindow(
		TEXT("EDIT"), TEXT(":GZ#"),
		WS_CHILD | WS_VISIBLE | WS_BORDER | ES_LEFT,
		100, 100, 200, 50,
		hwnd, NULL, hInst, NULL
		);

	if(sendCommand == NULL) {
		return false;
	}

	// ��M�����R�}���h��\������
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


// COM�|�[�g�̏�����
bool initCOMPort() {
	// �V���A���|�[�g���J��
	comPort = CreateFile(
		TEXT("COM4"), GENERIC_READ | GENERIC_WRITE, 
		0, NULL, OPEN_EXISTING, 0, NULL);
	if(comPort == NULL) {
		return false;
	}

	DCB dcb;	// �V���A���|�[�g�̍\����񂪓���\����
	GetCommState(comPort, &dcb);	// ���݂̐ݒ�l��ǂݍ���

	dcb.BaudRate = 2400;			// �{�[���[�g
	dcb.ByteSize = 8;				// �f�[�^��
	dcb.Parity = NOPARITY;			// �p���e�B
	dcb.StopBits = ONESTOPBIT;		// �X�g�b�v�r�b�g��
	dcb.fOutxCtsFlow = FALSE;		// ���M��CTS�t���[
	dcb.fRtsControl = RTS_CONTROL_ENABLE;	// RTS�t���[

	SetCommState(comPort, &dcb);	// �ύX�����ݒ�l����������

	return true;

}
