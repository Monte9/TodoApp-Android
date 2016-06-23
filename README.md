# Pre-work - *Todo App*

**Todo App** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Monte Thakkar**

Time spent: **20** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] Using DialogFragment to implement due date (calendar) functionality
* [x] Using seekbar to get priority (3 modes; low, Normal, HIGH)
* [x] Long press gesture on item to mark it as complete (Highlited GREEN)
* [x] High priority items are highlited RED

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

![App walkthrough](todo.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Challenges:
1. Knowing the difference between listView index and row id in SQLite. They are not directly mapped to each other and it took me a while to realised that in order to perform CRUD actions I needed to work on the row id of the todoitem as opposed to the index of the item on the listView.
2. After adding a new column, I had trouble getting my Cursor to work properly. Hence I had to bump up the version number of the db table as well as uninstall the app from the phone. 
3. Since this was my first Android app it took me a while to implement features the way I envisioned it in my head. Had to do a lot of googling on how to implement different functionality and put it all together to acheive desired effect. 

## License

    Copyright [2016] [Monte Thakkar]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
