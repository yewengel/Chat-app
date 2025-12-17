# Java Client-Server Chat Application

## Table of Contents

* [Introduction](#introduction)
* [Features](#features)
* [Requirements](#requirements)
* [Setup and Installation](#setup-and-installation)
* [How to Use](#how-to-use)
* [Profile Management](#profile-management)
* [Themes](#themes)
* [Exiting the Chat](#exiting-the-chat)
* [About](#about)
* [Screenshots](#screenshots)
* [License](#license)

## Introduction

This project is a Java-based Client-Server Chat Application that allows multiple users to connect and communicate in real-time. The application uses Java Socket programming for network communication and Swing for the graphical user interface.

## Features

* Real-time messaging between multiple clients.
* Client-side profile management (Full Name, Email, Bio).
* Customizable themes.
* GUI-based interface using Swing.
* Easy to use with a pre-chat input form.
* Exit confirmation with goodbye message.

## Requirements

* Java JDK 8 or higher
* IDE (Eclipse, IntelliJ, or VS Code)
* Basic understanding of Java networking and Swing

## Setup and Installation

1. **Clone the repository**

```bash
git clone <repository_url>
cd <repository_folder>
```

2. **Compile the Server and Client**

```bash
javac Server.java
javac Client.java
```

3. **Run the Server**

```bash
java Server
```

4. **Run the Client**

```bash
java Client
```

> Ensure the server is running before launching the client.

## How to Use

1. Launch the `Server.java` to start the chat server.
2. Launch `Client.java`.
3. Fill in the pre-chat form:

   * Server IP (e.g., 127.0.0.1)
   * Username
   * Full Name (optional)
   * Email (optional)
   * Short Bio (optional)
4. Click OK to enter the chat.
5. Type your message and press **Enter** or **Send**.

## Profile Management

* **Edit Profile:** Allows users to update Full Name, Email, and Bio anytime.
* **View Profile:** Displays your profile information in a popup window.
* **Note:** Profile changes are client-side only and not broadcasted to other clients.

## Themes

You can customize the chat background through the `Theme` menu:

* Light
* Blue
* Dark
* Green

## Exiting the Chat

* Click the close button on the chat window.
* A confirmation dialog will appear.
* Confirming exit will send `/quit` to the server and close the application.

## About

The application demonstrates:

* Java socket programming
* Multithreading for handling multiple clients
* GUI design with Swing
* Client-server communication

## Screenshots

<img width="626" height="771" alt="image" src="https://github.com/user-attachments/assets/431f61ee-2df0-4993-85e5-219a67bbf22c" />
<img width="740" height="802" alt="image" src="https://github.com/user-attachments/assets/222b0a76-6747-4804-93f0-9974e85c1ec5" />

<img width="1543" height="919" alt="image" src="https://github.com/user-attachments/assets/b5ff8d3e-76f7-4cda-98ea-46305ba364f7" />


## License

This project is open-source and available under the MIT License.
