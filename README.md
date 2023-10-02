# MyChat Android Messaging App

## Description

**MyChat** is a powerful messaging application designed to connect people via instant messages. Built using Firebase for Authentication, Cloud Storage, and Realtime Database, MyChat ensures a seamless and secure messaging experience.

## Tech Stack used:
- Java: Programming language
- Android Studio: Platform
- Firebase: Auth, Storage, Database, and Push Notification

## Features

- **Instant Messaging**: Send and receive text messages in real time.
- **User Authentication**: Sign up and login securely with Firebase Authentication.
- **Profile Management**: Customize your profile and manage your contact details.
- **Push Notifications**: Receive notifications for new messages.
- **Offline Support**: Messages are synchronized when the device comes online.
- **Text Recognition**: Text can be recognized from the image and sent via message.

### Authentication

- Firebase Authentication is used to handle user registration and login securely. It ensures user data remains private and protected.

### Cloud Storage

- Firebase Cloud Storage is employed for storing user profile images and images sent within the app.

### Realtime Database

- Firebase Realtime Database powers the core messaging functionality. It allows real-time synchronization of messages between users.

## Database Structure

```
mychat (Root Node)
│
├── ChatList
│   ├── uid1
│   │   ├── uid2: 
│   │   ├── uid3: 
│   │   └── ...
│   ├── uid2
│   │   ├── uid1: 
│   │   └── ...
│   └── ...
│
├── Chats
│   ├── chat1
│   │   ├── isseen: true
│   │   ├── message: "Hello!"
│   │   ├── receiver: "uid1"
│   │   ├── sender: "uid2"
│   │   └── ...
│   ├── chat2
│   │   ├── isseen: false
│   │   ├── message: "Hi there!"
│   │   ├── receiver: "uid3"
│   │   ├── sender: "uid1"
│   │   └── ...
│   └── ...
│
├── Tokens
│   ├── uid1: "token1"
│   ├── uid2: "token2"
│   ├── uid3: "token3"
│   └── ...
│
└── Users
    ├── uid1
    │   ├── uid: "uid1"
    │   ├── imageURL: "https://example.com/image1.jpg"
    │   ├── phone: "+1234567890"
    │   ├── status: "Online"
    │   └── username: "User1"
    ├── uid2
    │   ├── uid: "uid2"
    │   ├── imageURL: "https://example.com/image2.jpg"
    │   ├── phone: "+0987654321"
    │   ├── status: "Offline"
    │   └── username: "User2"
    └── ...
```

## Getting Started

1. Clone the MyChat repository from [GitHub](https://github.com/prashantgarbuja/mychat).
2. Open the project in Android Studio.
3. Set up Firebase for your project by following the [steps](https://firebase.google.com/docs/android/setup) in the [Firebase Console](https://console.firebase.google.com/).
4. Update the Firebase configuration in your app according to your own Firebase project details.
5. In Firebase Console, configure [security rules](https://firebase.google.com/docs/rules) as per requirements.
6. Enable Firebase Cloud Messaging (push notification) and Cloud Vision API (text recognition).
7. Build and run the app on an Android device or emulator.

## Screenshots

<img src="https://github.com/prashantgarbuja/mychat/blob/main/Screenshots/1.jpg" alt="screenshots1" width="300" > <img src="https://github.com/prashantgarbuja/mychat/blob/main/Screenshots/2.jpg" alt="screenshots2" width="300" > <img src="https://github.com/prashantgarbuja/mychat/blob/main/Screenshots/3.jpg" alt="screenshots3" width="300" > <img src="https://github.com/prashantgarbuja/mychat/blob/main/Screenshots/4.jpg" alt="screenshots4" width="300" > <img src="https://github.com/prashantgarbuja/mychat/blob/main/Screenshots/5.jpg" alt="screenshots5" width="300" > <img src="https://github.com/prashantgarbuja/mychat/blob/main/Screenshots/6.jpg" alt="screenshots6" width="300" > <img src="https://github.com/prashantgarbuja/mychat/blob/main/Screenshots/7.jpg" alt="screenshots7" width="300" > <img src="https://github.com/prashantgarbuja/mychat/blob/main/Screenshots/8.jpg" alt="screenshots8" width="300" >
