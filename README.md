# Election
An election interface with vote and view

Features:
1	Ensure that each user votes only once.
a.	The program checks for if the user has voted by keeping a list of voter’s numbers of those who has voted. Before user votes, the system will compare the voter’s number entered by the user to all voter’s number that voted in the past. If the voter’s number already exists in the file, vote won’t proceed.
2	The voting records remain consistent even when it is accessed concurrently by multiple clients. 
a.	The records remain consistent by invoking java synchronized method in the vote function, which is the only function that alters the values and keys in the file.
3	All votes are safely stored even when the server process crashes.
a.	Both voter’s number list and the vote result table are stored locally. The changes write to a local file. The program queries the vote result by accessing local data.

