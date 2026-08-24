Sk Mini Payment Bank User Android App

This app is a mobile wrapper for the SK Mini Bank website so users can log in and access their bank account from Android.

How to use:
1. Open this folder in Android Studio.
2. Let Gradle sync finish.
3. Update the bank URL in:
   app/src/main/res/values/strings.xml
   - default: https://skminibank-1.onrender.com/login.jsp
4. If your app is running on an emulator or real device, ensure the server is accessible.
5. If you want to use a local server:
   http://10.0.2.2:8080/skminibank/ (emulator)
   http://192.168.0.12:8080/skminibank/ (real device IP)
6. Run the app on an emulator or phone.

The app loads the website in a WebView and lets the customer use the normal login flow of the website.
