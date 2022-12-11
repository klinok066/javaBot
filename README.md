# Bot for learning English word (flash cards with English word)

**Task:** создать Restful API  для бота и навыка Алисы по изучению английского

**API functionality:**

1. Add new word to the database (English only)
2. Ability to practice learned word (all at once or in group)
3. Word repetition reminder

## Adding new word

To add a word:

1. Specify a specific group to which it will be added (required parameter)
2. Set word translation (required parameter): you can specify the translation of the word yourself or use automatic translation
3. Word to be added

## Practicing learned word

Training should be: for all learned, for some part, for the entire group of word or for part of the learned word

### For all learned word

`/test_all <mode>` - start testing for all word from the database

There is a **hard** and **easy** mode

**Hard mode** - you need to write the bot itself the translation of the word

**Easy mode** - you need to choose 1 of 4 translations

**The default is light mode**, i.e. if you do not specify which mode, the server will automatically select light

The learned word are checked until the user writes `/stop_test`.

### For some part of the word

`/test <number of word> <mode>` - start testing on some part of the word from the entire database

The user in this mode writes the number of word to check, the default value is 10

Mode selection is the same as in the previous section

### For all word from the group

`/test_in_group_all <group> <mode>` - start testing for all word from the group

Here is the same mode selection as in the first section.

To stop testing, you need to write the command `/stop_test`

### According to some word from the group

`/test_in_group <group> <amount> <mode>` - start testing on a certain number of word from the group

The mode selection is the same as the first section

## Word repetition reminder

Once a day, a notification should have been made in the bot, which sounds like this: *Oh, what time is it right now? That's right, it's time to learn English!*

## List of commands:

`/start` - start the bot

`/group_list` - list of all group

`/group_create <name>` - create a group

`/word_list` - list of all paginated word

`/word_add <word> <translation> <group>` - adding a word. You can specify the translation yourself, but if you write `_translate`, then the translation will be automatic through the Yandex API, the group parameter is optional, all the rest are required

`/word_to <word> <group>` - redefining the group of an existing word

`/test_all <mode>` - start testing for all word from the database

`/test <number of word> <mode>` - start testing on some part of the word from the entire database

`/test_in_group_all <group> <mode>` - start testing for all word from the group

`/test_in_group <group> <amount> <mode>` - start testing on a certain number of word from the group

`/stop_test` - end testing (for `/test_all` and for `test_in_group_all`)

## Architecture:

    -----------------------  --------------------------
    | Alice command block |  | Telegram command block |
    -----------------------  --------------------------
                     ↑↓          ↑↓
        -------------------------------------------
        |            Request handler              |
        -------------------------------------------
                            ↑↓
                    -------------------
                    |       API       |
                    -------------------
                            ↑↓   
                    -------------------
                    |     Database    |
                    -------------------
                    
## Steps:

### Step 1
