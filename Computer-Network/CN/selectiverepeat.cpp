#include <iostream>
#include <vector>
using namespace std;

void selectiveRepeat(int totalFrames, int windowSize, int errorFrame)
{
    cout << "\n=== SELECTIVE REPEAT ARQ SIMULATION ===\n";
    vector<bool> received(totalFrames, false);
    int nextframe = 0;
    bool errorOccurred = false;

    while (nextframe < totalFrames)
    {
        cout << "\nSender Window: ";
        for (int i = nextframe; i < nextframe + windowSize && i < totalFrames; i++)
            if (!received[i])
                cout << i << " ";
        cout << endl;

        for (int i = nextframe; i < nextframe + windowSize && i < totalFrames; i++)
        {
            if (!received[i])
            {
                cout << "Sender: Sending Frame " << i << endl;

                if (i == errorFrame && !errorOccurred)
                {
                    cout << "Receiver: Error! Frame " << i << " lost.\n";
                    cout << "Receiver: Will wait for resend of Frame " << i << endl;
                    errorOccurred = true;
                }
                else
                {
                    cout << "Receiver: Frame " << i << " received successfully.\n";
                    cout << "Receiver: Sending ACK " << i << endl;
                    received[i] = true;
                }
            }
        }

        // Slide window ahead only for acknowledged frames
        while (nextframe < totalFrames && received[nextframe])
            nextframe++;
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

    selectiveRepeat(totalFrames, windowSize, errorFrame);
    return 0;
}
