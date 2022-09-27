# Bot for learning English words (flash cards with English words)

**Task:** создать Restful API  для бота и навыка Алисы по изучению английского

**API functionality:**

1. Add new words to the database (English only)
2. Ability to practice learned words (all at once or in groups)
3. Word repetition reminder

## Adding new words

To add a word:

1. Specify a specific group to which it will be added (required parameter)
2. Set word translation (required parameter): you can specify the translation of the word yourself or use automatic translation
3. Word to be added

## Practicing learned words

Training should be: for all learned, for some part, for the entire group of words or for part of the learned words

### For all learned words

`/test_all <mode>` - start testing for all words from the database

There is a **hard** and **easy** mode

**Hard mode** - you need to write the bot itself the translation of the word

**Easy mode** - you need to choose 1 of 4 translations

**The default is light mode**, i.e. if you do not specify which mode, the server will automatically select light

The learned words are checked until the user writes `/stop_test`.

### For some part of the words

`/test <number of words> <mode>` - start testing on some part of the words from the entire database

The user in this mode writes the number of words to check, the default value is 10

Mode selection is the same as in the previous section

### For all words from the group

`/test_in_group_all <group> <mode>` - start testing for all words from the group

Here is the same mode selection as in the first section.

To stop testing, you need to write the command `/stop_test`

### According to some words from the group

`/test_in_group <group> <amount> <mode>` - start testing on a certain number of words from the group

The mode selection is the same as the first section

## Word repetition reminder

Once a day, a notification should have been made in the bot, which sounds like this: *Oh, what time is it right now? That's right, it's time to learn English!*

## List of commands:

`/start` - start the bot

`/group_list` - list of all groups

`/group_create <name>` - create a group

`/word_list` - list of all paginated words

`/word_add <word> <translation> <group>` - adding a word. You can specify the translation yourself, but if you write `_translate`, then the translation will be automatic through the Yandex API, the group parameter is optional, all the rest are required

`/word_to <word> <group>` - redefining the group of an existing word

`/test_all <mode>` - start testing for all words from the database

`/test <number of words> <mode>` - start testing on some part of the words from the entire database

`/test_in_group_all <group> <mode>` - start testing for all words from the group

`/test_in_group <group> <amount> <mode>` - start testing on a certain number of words from the group

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
