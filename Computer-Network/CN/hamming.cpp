#include <iostream>
using namespace std;

int main()
{
    int n = 4;
    cout << "Enter 4-bit data: ";
    int arr[n];
    for (int i = 0; i < n; i++)
    {
        cin >> arr[i];
    }

    int code[7];

    // Assign data bits to correct Hamming positions
    // p1 p2 d1 p3 d2 d3 d4
    code[2] = arr[0]; // d1
    code[4] = arr[1]; // d2
    code[5] = arr[2]; // d3
    code[6] = arr[3]; // d4

    // Calculate parity bits
    code[0] = code[2] ^ code[4] ^ code[6];         // p1
    code[1] = code[2] ^ code[5] ^ code[6];         // p2
    code[3] = code[4] ^ code[5] ^ code[6];         // p3

    cout << "\nEncoded 7-bit Hamming code: ";
    for (int i = 0; i < 7; i++)
    {
        cout << code[i] << " ";
    }

    // Receive array
    int recv[7];
    cout << "\nEnter received 7-bit code: ";
    for (int i = 0; i < 7; i++)
    {
        cin >> recv[i];
    }

    // Calculate parity checks (c1, c2, c3)
    int c1 = recv[0] ^ recv[2] ^ recv[4] ^ recv[6];
    int c2 = recv[1] ^ recv[2] ^ recv[5] ^ recv[6];
    int c3 = recv[3] ^ recv[4] ^ recv[5] ^ recv[6];

    int errorPos = c3 * 4 + c2 * 2 + c1; // From right (1-based)

    if (errorPos == 0)
    {
        cout << "\nNo error detected!";
    }
    else
    {
        // Convert to left side (1 = leftmost bit)
        int leftPos = 8 - errorPos; // since 7 bits total
        cout << "\nError detected at position (from LEFT): " << leftPos;

        // Correct the error
        recv[leftPos - 1] = (recv[leftPos - 1] == 0) ? 1 : 0;

        cout << "\nCorrected code: ";
        for (int i = 0; i < 7; i++)
        {
            cout << recv[i] << " ";
        }
    }

    return 0;
}
