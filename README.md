# Student Question and Answer System
This repository contains the Phase 1 implementation of a user account management application developed as part of a team project. The application includes functionality for creating new users, logging in and out, and updating account information.

In Phase 1, the following features were implemented:

# New User Functionality

First user to register is automatically assigned the Admin role.
Users can create accounts with:

  Username (5–20 alphanumeric characters)
  Password (≥8 characters, must include at least one number and one special character)
  Email
  First Name and Last Name (letters only)

Input validation provides clear error messages when entries are invalid.
Users are required to log in again after account creation.

# Login and Logout

Users can log in with a username and password.
The system verifies credentials and prevents access if invalid.
Users can log out at any time, closing their session securely.

# Admin Basics

Admin can access a dedicated home page after login.
Admin can generate one-time invitation codes for new users.
Admin cannot remove their own account or admin role.
Admin can view user information, including username, email, and role(s).

# User Experience and Interface

Simple and clear navigation flow.
Immediate feedback on actions like login errors or account updates.
Consistent input fields and descriptive buttons.
Basic accessibility with labels and focusable fields.

# To Start

run StartCSE360.java with configurations for FoundationCode
