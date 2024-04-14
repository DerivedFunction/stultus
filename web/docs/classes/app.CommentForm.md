[web-bridge-tutorial](../README.md) / [Modules](../modules.md) / [app](../modules/app.md) / CommentForm

# Class: CommentForm

[app](../modules/app.md).CommentForm

CommentForm is all the code to add comments to a post

## Table of contents

### Constructors

- [constructor](app.CommentForm.md#constructor)

### Properties

- [author](app.CommentForm.md#author)
- [body](app.CommentForm.md#body)
- [container](app.CommentForm.md#container)
- [id](app.CommentForm.md#id)
- [textbox](app.CommentForm.md#textbox)
- [title](app.CommentForm.md#title)

### Methods

- [clearForm](app.CommentForm.md#clearform)
- [clickEdit](app.CommentForm.md#clickedit)
- [createTable](app.CommentForm.md#createtable)
- [getComment](app.CommentForm.md#getcomment)
- [init](app.CommentForm.md#init)
- [submitForm](app.CommentForm.md#submitform)

## Constructors

### constructor

• **new CommentForm**(): [`CommentForm`](app.CommentForm.md)

Intialize the object b setting buttons to do actions
when clicked

#### Returns

[`CommentForm`](app.CommentForm.md)

#### Defined in

[app.ts:979](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-979)

## Properties

### author

• **author**: `HTMLElement`

The HTML element for post author

#### Defined in

[app.ts:958](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-958)

___

### body

• **body**: `HTMLElement`

The HTML element for post body

#### Defined in

[app.ts:953](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-953)

___

### container

• **container**: `HTMLElement`

The HTML element for the container of the entire module

#### Defined in

[app.ts:968](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-968)

___

### id

• **id**: `HTMLInputElement`

The HTML element for post id

#### Defined in

[app.ts:963](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-963)

___

### textbox

• **textbox**: `HTMLTextAreaElement`

The HTML element for comment body

#### Defined in

[app.ts:973](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-973)

___

### title

• **title**: `HTMLElement`

The HTML element for post title

#### Defined in

[app.ts:948](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-948)

## Methods

### clearForm

▸ **clearForm**(): `void`

clears the contents of the container

#### Returns

`void`

#### Defined in

[app.ts:1153](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1153)

___

### clickEdit

▸ **clickEdit**(`e`): `void`

clickEdit is the code we run in response to a click of a edit button

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `e` | `Event` | Event to edit a Comment |

#### Returns

`void`

#### Defined in

[app.ts:1117](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1117)

___

### createTable

▸ **createTable**(`data`): `Promise`\<`void`\>

Create a table of comments

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `data` | `any` | JSON of all comments from post |

#### Returns

`Promise`\<`void`\>

#### Defined in

[app.ts:1060](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1060)

___

### getComment

▸ **getComment**(`data`): `void`

Get comments

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `data` | `any` | JSON object of all comments to a post |

#### Returns

`void`

#### Defined in

[app.ts:1027](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1027)

___

### init

▸ **init**(`data`): `Promise`\<`void`\>

Initialize the object by setting the post's content to form

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `data` | `any` | The data object received from the server |

#### Returns

`Promise`\<`void`\>

#### Defined in

[app.ts:993](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-993)

___

### submitForm

▸ **submitForm**(): `void`

#### Returns

`void`

#### Defined in

[app.ts:1164](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1164)
