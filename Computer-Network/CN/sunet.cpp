#include<iostream>
#include<string>
#include<sstream>
#include<cmath>
using namespace std;

int check(string part)
{
    int num = stoi(part);
    if (num >= 1 && num < 126)
        return 8;   // Class A
    else if (num >= 128 && num <= 191)
        return 16;  // Class B
    else if (num >= 192 && num <= 223)
        return 24;  // Class C
    return 0;
}

// count number of 1's in binary of subnet mask
int maskToCIDR(string mask)
{
    stringstream ss(mask);
    string octet;
    int cidr = 0;

    while (getline(ss, octet, '.'))
    {
        int num = stoi(octet);
        for (int i = 7; i >= 0; i--)
        {
            if (num & (1 << i))
                cidr++;
        }
    }
    return cidr;
}

int main()
{
    string IP, part, mask;
    cout << "Enter IP Address : ";
    cin >> IP;
    cout << "Enter Subnet Mask : ";
    cin >> mask;

    // Get first octet to determine class
    for (int i = 0; i < IP.size(); i++)
    {
        if (IP[i] == '.')
            break;
        part += IP[i];
    }

    int D_class = check(part);
    if (D_class == 0)
    {
        cout << "Invalid IP Class!" << endl;
        return 0;
    }

    int CIDR = maskToCIDR(mask);
    cout << "\nCIDR Notation : /" << CIDR << endl;

    int borrowedBits = CIDR - D_class;
    int totalSubnets = pow(2, borrowedBits);
    int hostBits = 32 - CIDR;
    int hostsPerSubnet = pow(2, hostBits) - 2;

    cout << "Number of Subnets : " << totalSubnets << endl;
    cout << "Number of Hosts per Subnet : " << hostsPerSubnet << endl;

    // Split IP into 4 parts
    stringstream ipStream(IP);
    string ipPart;
    int octets[4], i = 0;
    while (getline(ipStream, ipPart, '.'))
        octets[i++] = stoi(ipPart);

    // Calculate block size
    int blockSize = pow(2, (8 - (CIDR % 8)));
    if (CIDR % 8 == 0) blockSize = 256;

    int subnetStart = (octets[3] / blockSize) * blockSize;
    int subnetEnd = subnetStart + blockSize - 1;

    // Subnet (Network) Address
    cout << "\nSubnet (Network) Address : "
         << octets[0] << "." << octets[1] << "." << octets[2] << "." << subnetStart << endl;

    // First usable host address
    cout << "First Usable Host Address : "
         << octets[0] << "." << octets[1] << "." << octets[2] << "." << subnetStart + 1 << endl;

    // Last usable host address
    cout << "Last Usable Host Address : "
         << octets[0] << "." << octets[1] << "." << octets[2] << "." << subnetEnd - 1 << endl;

    // Broadcast address
    cout << "Broadcast Address : "
         << octets[0] << "." << octets[1] << "." << octets[2] << "." << subnetEnd << endl;

    return 0;
}
