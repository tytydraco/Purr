# Purr
Say hello to Purr, the all-in-one resolution changer for Android devices, no root required! How does it work you ask? We use Android SDK reflection to access hidden APIs to change the resolution of your phone screen. All we need is a special permission that can be granted using ADB.

# Why would you want to change the resolution of your display, anyway?
- Reducing the resolution of the display reduces the load on the GPU.
- Some games may fit a standard 16:9 display better than widescreen displays.

# Here's some benefits to using Purr:
- I calculate the DPI using some fancy math. Typically adjusting the DPI for new resolutions can be tricky because I need to determine the diagonal inches of each display, but instead I calculate the diagonal pixels of the display and divide it by the current DPI to determine a fair divisor. Then when a new resolution is applied, I divide the new diagonal pixels by this divisor to get a near identically-scaled DPI.
- We verify resolutions for compatibility before applying them for a sort of failsafe.
- Custom resolutions can be specified in the settings menu.
- Those with root can grant Purr permissions without using ADB. NOTE: Root is NOT required, it is optional.
- Android 6.0 (SDK 23) and above are supported using legacy APIs.
- Free warranty and support via email (tylernij@gmail.com) and Telegram (@tytydraco)
- You get to see a cute cat every time you open the app <3

# There is a limitation:
- Some Android versions may tamper with the resolution changing APIs, causing an error to be displayed. This is not the fault of Purr, but rather your OEM or ROM provider using non-standard APIs..

This project might be paid on Google Play, but it is open source on GitHub. It can be compiled using Android Studio Canary FOR FREE if you so wish to, however I do not offer direct support with compiling the app: https://www.github.com/tytydraco/Purr
