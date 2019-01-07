# Software Design labworks. #
##### Rss reader and storage for personal info #####
This little application can store your personal data with the firebase, and you may check it on any phone with this application later, by logging in. Also we provide rss reader to read different rss feeds with possibility to read full article and little cache storge for them, so you may read them in any time without internet!
![sda](http://i63.tinypic.com/otqazd.jpg)
## Architecture ##
This application is trying to use android architecture components and android best practices.
### Database ###
 As database for authentication and personal data storage we are using firebase. For getting data from firebase application provides manager and provider layers. 
 ##### Providers #####
 Providers just trying to make request to our database and grab data from it or doing some other things, just by catching Firebase exceptions and calling proper methods of some listener implementation.
 ##### Managers #####
 Managers are making our usage of providers much easier. When we want to grab our data, we are passing a listener implementation with responses on exceptions or success. In listener implmentation usually we change our MutableLiveData variable with current response state, and each LiveData observer will know about it. Also with this approach we don't have to suspend our main aplication while waiting for data.
 ### RSS components ###
 For grabbing data by url we are using [this lib](https://github.com/prof18/RSS-Parser) because it's pretty good for my purposes and it may save some time. For displaying RSS list and Cache list application uses RecyclerView. And for storing cache we are creating temp json file with data using Gson parser library.
 ### Main UI ###
 As for main UI, there is Activities for auth and Main application, and each activity have a fragment for navigation graph, in which we have our destination pages.

##### To run this application, just build it with the android studio 3.2+. #####
