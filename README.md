# Country-Pedia

This is an basic applicaiton which shows a list of countries in asia with their information.

I have used Retrofit to pull data from the REST api.

App follows MVVM architecture to accomplish caching of pulled data.
where the SQL database is created using Room.

created a repository class to intermediate between these two data sources.

Used a broadcast receiver that receives internet connectivity changes where the retrofit makes the pull from the base URL and then stores
data into the ROOM.

As the viewmodel listens to Livedata from the ROOM, the pulled data is directly listened by the viewmodels as updates UI accordingly.

Recyclerview with listadapter is used to present this data, where DIFFUTILS does it work to differentiate between different datasets accordingly.

I have skipped the descriptive comments part here.
