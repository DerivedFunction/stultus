[web-bridge-tutorial](../README.md) / [Modules](../modules.md) / [app](../modules/app.md) / ElementList

# Class: ElementList

[app](../modules/app.md).ElementList

ElementList provides a way to see the data stored in server

## Table of contents

### Constructors

- [constructor](app.ElementList.md#constructor)

### Properties

- [container](app.ElementList.md#container)

### Methods

- [clickComment](app.ElementList.md#clickcomment)
- [clickDelete](app.ElementList.md#clickdelete)
- [clickDownvote](app.ElementList.md#clickdownvote)
- [clickEdit](app.ElementList.md#clickedit)
- [clickUpvote](app.ElementList.md#clickupvote)
- [getMyProfile](app.ElementList.md#getmyprofile)
- [getUser](app.ElementList.md#getuser)
- [refresh](app.ElementList.md#refresh)
- [update](app.ElementList.md#update)

## Constructors

### constructor

• **new ElementList**(): [`ElementList`](app.ElementList.md)

#### Returns

[`ElementList`](app.ElementList.md)

## Properties

### container

• **container**: `HTMLElement`

The HTML element for the container of the entire module

#### Defined in

[app.ts:334](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-334)

## Methods

### clickComment

▸ **clickComment**(`e`): `void`

clickComment is the code we run in response to a click of a comment button

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `e` | `Event` | Event to get the message to be commented |

#### Returns

`void`

#### Defined in

[app.ts:703](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-703)

___

### clickDelete

▸ **clickDelete**(`e`): `void`

clickDelete is the code we run in response to a click of a delete button

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `e` | `Event` | Event to get the message to be deleted |

#### Returns

`void`

#### Defined in

[app.ts:560](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-560)

___

### clickDownvote

▸ **clickDownvote**(`e`): `void`

clickDownvote is the code we run in response to a click of a downvote button

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `e` | `Event` | Event to get the message to be downvoted |

#### Returns

`void`

#### Defined in

[app.ts:631](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-631)

___

### clickEdit

▸ **clickEdit**(`e`): `void`

clickEdit is the code we run in response to a click of a edit button

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `e` | `Event` | Event to get the message to be editGender |

#### Returns

`void`

#### Defined in

[app.ts:666](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-666)

___

### clickUpvote

▸ **clickUpvote**(`e`): `void`

clickUpvote is the code we run in response to a click of a upvote button

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `e` | `Event` | Event to get the message to be upvoted |

#### Returns

`void`

#### Defined in

[app.ts:595](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-595)

___

### getMyProfile

▸ **getMyProfile**(): `Promise`\<`number`\>

Simple ajax to call current user profile

#### Returns

`Promise`\<`number`\>

UserData of email, username, gender, SO, and note

#### Defined in

[app.ts:406](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-406)

___

### getUser

▸ **getUser**(`userid`): `Promise`\<`any`\>

Simple ajax to call user information based on user id

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `userid` | `any` | the userid to find information |

#### Returns

`Promise`\<`any`\>

UserDataLite of email, username, and note

#### Defined in

[app.ts:371](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-371)

___

### refresh

▸ **refresh**(): `void`

Refresh updates the messageList

#### Returns

`void`

#### Defined in

[app.ts:339](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-339)

___

### update

▸ **update**(`data`): `Promise`\<`void`\>

Simple ajax to update current user profile

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `data` | `any` | the /messages JSON object to parse information |

#### Returns

`Promise`\<`void`\>

#### Defined in

[app.ts:435](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-435)
