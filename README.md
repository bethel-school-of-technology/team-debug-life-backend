# Details

### How to request

*All requests should be prepended with or proxied via `Localhost:8080` in testing environments*
- `/Login` Returns OK & Auth token in header if the request is approved
  - Method: **POST**
  - Data: **{ "username", "password" }**
- `/Register` Returns OK & User object if the request is approved
  - Method: **POST**
  - Data: **{ "username", "password" }**

### How to use

To start the server, in VS Code, search 'Spring' in extensions. Download the first 5 listed: Spring Boot, Spring Initializr, Spring Dashboard, Spring Extension Pack, Java Extension Pack.

Then, after they have been installed, select the application `JavaApplcation.java` script under `Java\src\main\java\com\debuglife`. If a pop-up appears asking you to import the files, hit Yes. Lastly, select the play button in the upper-right corner of VS Code. Hit `Run Java` in the dropdown.

To close the server, just exit the terminal or hit `CTRL+C` when the terminal is in focus.

### Notes:

Within your React project's `package.json`, you can add `"proxy": "http://localhost:8080",` and use all request endpoints (`/login`, `/register`, etc) without having to prepend each request URL with `http://localhost:8080`.
