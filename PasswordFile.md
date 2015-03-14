# Password File #

Passwords are used to jump from an episode to another in story mode. They are stored in the .pwd file.

## Old documentation ##
The password files are read when the user enters the menu, in MenuInit();. The password is read to a structure like this:

```
typedef struct PASSWORD_TAG2{
	int pass[4];
	int kentta;
}PASSWORD_F;  
```

This structure is for one password and all the passwords are stored in an array of these, allocated using malloc();. The `int pass[4]` array is for storing all the password values (the four images) in a correct order and kentta is used to tell the level to which the player gets if he/she knows this password.

For the reading a function `PASSWORD *LoadPassword(const char *szFilename)` is used. It can be found in menu.cpp.

_Jarkko Laine, 7.11.1999_

### loadPassword() ###
```
//-[ LoadPassword ]---------------------------------------------------------
//
// Description:		Loads the passwords from a file
//
// Return value:	the passwords
//

PASSWORD *LoadPassword(const char *szFilename)
{
	int file_handle;  // the file handle
    OFSTRUCT file_data;// the file data information
	
	PASSWORD_F *pass;
	PASSWORD *password;
	
	int PW_COUNT;


	// open the file if it exists
	if ((file_handle = OpenFile(szFilename,&file_data,OF_READ))==-1)
		return(0);

	// First read the number of the passwords
	_lread(file_handle, &PW_COUNT, sizeof(int));

	// First allocate some memory
	pass=(PASSWORD_F *)malloc(PW_COUNT*sizeof(PASSWORD_F));
	password=(PASSWORD *)malloc(PW_COUNT*sizeof(PASSWORD));
	
    password[0].count=PW_COUNT;


	// Read the rest of the data
	for(int t=0;t<PW_COUNT;t++){
		_lread(file_handle, &pass[t], sizeof(PASSWORD_F));
	}

	// And copy the data to the right place
	for(t=0;t<PW_COUNT;t++){
		password[t].pass[0]=pass[t].pass[0];
		password[t].pass[1]=pass[t].pass[1];
		password[t].pass[2]=pass[t].pass[2];
		password[t].pass[3]=pass[t].pass[3];
		password[t].kentta=pass[t].kentta;
	}

	free(pass);

	return password;
}
```