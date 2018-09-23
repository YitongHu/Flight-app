The phase3 project can be found in the directory, ~/group_0500/p3.

In order to login to the app, and use other app functions such as searching for flights and creating itinieraries, you must first launch the Android Device Monitor and locate the directory /data/data/cs.b07.phase3/files. From there you will need to push blank .csv files with the following names:

1) "passwords.csv"�
2) "clients.csv"
3) "flights2.csv"�
4) "booking.csv"�


These files must be stored within the application in order for the application to function.

Once the application is launched via emulation, the user will be required to login;

1) If the user is a client and they login using their correct credentials; they will be redirected to a client page where they can: 
	a) Book an Itinerary 
	b) Search for Flights 
	c) Edit their personal information 
	d) View Booked Itineraries.
	
a) In the; Book Itinerary Screen: The client will be able to search for itineraries by providing the Origin�, Destination� and Date of Departure.

b) In the; Search for Flights Screen: The client will be able to search for flights by providing the “Origin”, “Destination” and “Date of Departure”.

c) In the; Edit Personal Information Screen: The client will be able to edit their personal and billing information.

d) In the; View Booked Itineraries Screen: The client will be able to view all the itineraries that they have booked.

2) If the user is an admin and they login using their correct credentials; they will be redirected to the admin page where they can:
	a) Search for Clients, 
	b) Search for Flights, 
	c) Upload Clients, 
	d) Upload Flights.
	
a) In the; Search for Clients Screen: The Admin will be able to search for clients that are already registered in the system; by providing the client’s “Username”

b) In the; Search for Flights Screen: The Admin will be able to search for flights that are registered in the system; by providing the “Origin”, “Destination” and “Date of Departure”

c) In the; Upload Clients Screen: The Admin will be able to upload a client into the database (clients.csv), which is stored in the application’s storage. Admin needs to provide the Client’s .csv 	   file that contains all their personal and billing information.

d) In the; Upload Flights Screen: The Admin will be able to upload a flight into the database (flights.csv), which is stored in the application’s storage. Admin needs to provide the Flight’s .csv 	   file that contains all the flight information.



