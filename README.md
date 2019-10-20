# CPSC2810 Cybersecurity Project
>-Armaan Gill, Erik Dengler, Jake Lee

The goal of this project is to design and implement the security measures to support
the ABC Airline business. The company has headquarter and two branches located in three
separate cities. The project should address all of the following security requirements with
proper contorls studied in the class.

![Program Flowchart](/flowcharts/network$#32;security.png)


## Security Measures

### Communication Mechanism

Communication mechanism ensures secure communication via insecure public network among headquarter
and branches, and thus it provides authenticated, confidential, accurate and consistent communication.
Once a user initiates a session with the server, a session key is created. This key is then used to encrypt data and decrypt as per the following flowchart.
* Flowchart :
![Communication Mechanism Flowchart](/flowcharts/CommMechFlowchart.png)
* Code : `TODO`


### Protection Mechanism

Through protection mechanism, different users are allowed to handle different assets in some proper way based on policy.


#### - User Access Control 
The server will have an access matrix of users and files. If reading a file, the server checks if it exists in the matrix. If it exists it then checks in the matrix if the current user has the required permission to access a file. If they do then they will encrypt and send the file (see flowchart under secure communication for encryption). To write a file, it checks if the user has the required write permission, if they do then the file is encrypted (see secure communication) and written to the database.
  - Flowchart : 
![Asset Protection Flowchart](/flowcharts/Asset&#32;protection.png)
  - Code : `TODO`

#### - User Login Session Control 
To connect to the server, a user will have to go through the following steps indicated in the flowchart
to authenticate the user
  - Flowchart :
![Initial login Flowchart](/flowcharts/Initial&#32;login.png)
  - Code : `TODO`





