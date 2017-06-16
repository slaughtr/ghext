pext
======

#### By [Dallas Slaughter](https://www.linkedin.com/in/dallas-slaughter) -  [email](mailto:dslaughtr@gmail.com)


pext is a text editor with Pastebin integration. It is meant to simplifly sharing code snippets and other text-based items on the go. It allows a user to login to Pastebin, submit text to Pastebin, and see a list of their own pastes and trending pastes.

When submitting to Pastebin, the user is given several options: Paste title, Paste privacy, Paste expiration, and Paste syntax highlighting. 

The Paste does not require a title, and will default to Untitled if none is provided.

Privacy only works when logged in. The three options are: Public, Unlisted, and Private. Public includes your Paste in the Pastebin index, Unlisted does not. Private means only you can see it.

Expiration works much as you might expect. When the amount of time you specify is up, the Paste expires and is deleted from Pastebin.

I have not included _all_ options for syntax highlighting in the app, as many options seemed like a little too much (and made finding your preferred syntax quite difficult).

####NOTE
While you don't NEED a [Pastebin](https://www.pastebin.com) account to use this app, most features won't work unless you login. Without logging in, you can still submit new pastes and see the list of trending pastes. When logged in, you can see all of your own pastes and have many more options when submitting a paste.

## Settings
Settings will be saved to your device's Shared Preferences AND to Firebase. The app loads shared preferences intially, and only loads from Firebase when you login. I will eventually change how this works a little, depending on feedback.

## Screenshots

![some screens](http://40two.net/pext/screens.png)

## Setup/Installation Requirements

* _Clone this repo to your local machine_
* _Open in Android Studio 3.0_
* _Run in emulator or on your connected Android device_

## Missing Features

* Color options for editor
* Ability to delete user's own pastes
* More info in trending/own pastes lists
* Firebase fine-tuning concerning number of items in clipboard history
* General styling and UI improvements. IE: better colors and positioning of items
* A few things the [Pastebin API](https://pastebin.com/api) is capable of that I didn't implement, like getting a paste by it's key
* A brackets button for easier insertion of certain common characters
* Save/load files from device. This may never actually happen.
* Some loading indicators
* Syntax highlighting (https://github.com/nakshay/TextHighlighter ?)


## Known Bugs

_ Occasionally the login popup will cause the app to crash when you click login. The fragment just can't getActivity occasionally. I have no idea why. 

The "Welcome back," and "You are not logged in" texts don't always update appropriately. This will be fixed in future updates.

There are some settings that need to be implemented still._

## Support and contact details

_If you have any issues or questions (or even suggestions!), email me at the address listed above._

## Special Thanks

[xgouchet](https://github.com/xgouchet) for making [TED](https://github.com/xgouchet/Ted). I borrowed his AdvancedEditText view, which saved me a ton of time (and looks great!).

[smart-fun](https://github.com/smart-fun) for [XmlToJson](https://github.com/smart-fun/XmlToJson). This tool was indespensible for my API calls, and their help in solving some API call issues saved the project.

My instructor at [Epicodus](https://www.epicodus.com), [Perry Eising](https://github.com/PerrySetGo) for being incredibly helpful and understanding along the way.

And the Pastebin admin for whitelisting my IP so I could do testing!

## Technologies Used

_Android Studio, Java, XML, JSON_

### License

_MIT License_

Copyright (c) 2017 - Dallas Slaughter

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
