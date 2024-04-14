[web-bridge-tutorial](../README.md) / [Modules](../modules.md) / app

# Module: app

## Table of contents

### Classes

- [CommentEditForm](../classes/app.CommentEditForm.md)
- [CommentForm](../classes/app.CommentForm.md)
- [EditEntryForm](../classes/app.EditEntryForm.md)
- [ElementList](../classes/app.ElementList.md)
- [NewEntryForm](../classes/app.NewEntryForm.md)
- [Profile](../classes/app.Profile.md)
- [ViewUserForm](../classes/app.ViewUserForm.md)

### Variables

- [$](app.md#$)
- [backendUrl](app.md#backendurl)
- [comment](app.md#comment)
- [commentEditForm](app.md#commenteditform)
- [commentForm](app.md#commentform)
- [editEntryForm](app.md#editentryform)
- [mainList](app.md#mainlist)
- [messages](app.md#messages)
- [newEntryForm](app.md#newentryform)
- [profile](app.md#profile)
- [user](app.md#user)
- [viewUserForm](app.md#viewuserform)

### Functions

- [InvalidContentMsg](app.md#invalidcontentmsg)
- [getAllUserBtns](app.md#getalluserbtns)
- [hideAll](app.md#hideall)

## Variables

### $

• **$**: `any`

Prevent compiler errors when using jQuery by test
setting $ to any

#### Defined in

[app.ts:6](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-6)

___

### backendUrl

• `Const` **backendUrl**: ``""``

Backend server link to dokku

#### Defined in

[app.ts:18](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-18)

___

### comment

• `Const` **comment**: ``"comment"``

Component name to fetch resources

#### Defined in

[app.ts:34](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-34)

___

### commentEditForm

• **commentEditForm**: [`CommentEditForm`](../classes/app.CommentEditForm.md)

Global variable to be referenced for commentEditForm

#### Defined in

[app.ts:1202](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1202)

___

### commentForm

• **commentForm**: [`CommentForm`](../classes/app.CommentForm.md)

Global variable to be referenced for CommentForm

#### Defined in

[app.ts:938](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-938)

___

### editEntryForm

• **editEntryForm**: [`EditEntryForm`](../classes/app.EditEntryForm.md)

Global variable to be referenced for ElementList

#### Defined in

[app.ts:156](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-156)

___

### mainList

• **mainList**: [`ElementList`](../classes/app.ElementList.md)

Global variable to be referenced for ElementList

#### Defined in

[app.ts:323](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-323)

___

### messages

• `Const` **messages**: ``"messages"``

Component name to fetch resources

#### Defined in

[app.ts:24](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-24)

___

### newEntryForm

• **newEntryForm**: [`NewEntryForm`](../classes/app.NewEntryForm.md)

Global variable to be referenced for newEntryForm

#### Defined in

[app.ts:12](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-12)

___

### profile

• **profile**: [`Profile`](../classes/app.Profile.md)

Global variable to be referenced for Profile

#### Defined in

[app.ts:740](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-740)

___

### user

• `Const` **user**: ``"user"``

Component name to fetch resources

#### Defined in

[app.ts:29](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-29)

___

### viewUserForm

• **viewUserForm**: [`ViewUserForm`](../classes/app.ViewUserForm.md)

Global variable to be referenced for commentEditForm

#### Defined in

[app.ts:1361](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1361)

## Functions

### InvalidContentMsg

▸ **InvalidContentMsg**(`message`, `error`): `void`

Display error message from HTML index for a moment

#### Parameters

| Name | Type |
| :------ | :------ |
| `message` | `any` |
| `error` | `any` |

#### Returns

`void`

#### Defined in

[app.ts:1539](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1539)

___

### getAllUserBtns

▸ **getAllUserBtns**(): `void`

Gets all user links to link it to a user page

#### Returns

`void`

#### Defined in

[app.ts:1505](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1505)

___

### hideAll

▸ **hideAll**(): `void`

Hides every module

#### Returns

`void`

#### Defined in

[app.ts:1525](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1525)
