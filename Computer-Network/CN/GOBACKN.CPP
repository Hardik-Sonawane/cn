#include <iostream>
using namespace std;

void gBackN(int totalFrames, int windowSize, int errorFrame)
{
    cout << "\n=== GO-BACK-N ARQ SIMULATION ===\n";
    int nextframe = 0;
    bool errorOccurred = false;

    while (nextframe < totalFrames)
    {
        cout << "\nSender Window: ";
        for (int i = nextframe; i < nextframe + windowSize && i < totalFrames; i++)
            cout << i << " ";
        cout << endl;

        for (int i = nextframe; i < nextframe + windowSize && i < totalFrames; i++)
        {
            cout << "Sender: Sending Frame " << i << endl;

            if (i == errorFrame && !errorOccurred)
            {
                cout << "Receiver: Error! Frame " << i << " lost.\n";
                cout << "Receiver: Discarding remaining frames in this window.\n";
                cout << "Sender: Timeout occurred! Resending from Frame " << i << endl;
                errorOccurred = true;
                nextframe = i; // Go back to the lost frame
                break;
            }
            else
            {
                cout << "Receiver: Frame " << i << " received successfully.\n";
                cout << "Receiver: Sending ACK " << i << endl;
                nextframe = i + 1;
            }
        }
    }

    cout << "\nAll frames transmitted successfully.\n";
}

int main()
{
    int totalFrames, windowSize, errorFrame;

    cout << "Enter total number of frames: ";
    cin >> totalFrames;
    cout << "Enter window size: ";
    cin >> windowSize;
    cout << "Enter frame number to lose (simulate error): ";
    cin >> errorFrame;

    gBackN(totalFrames, windowSize, errorFrame);
    return 0;
}
