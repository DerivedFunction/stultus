[web-bridge-tutorial](../README.md) / [Modules](../modules.md) / [app](../modules/app.md) / CommentEditForm

# Class: CommentEditForm

[app](../modules/app.md).CommentEditForm

CommentEditForm contains all code for editing an comment

## Table of contents

### Constructors

- [constructor](app.CommentEditForm.md#constructor)

### Properties

- [container](app.CommentEditForm.md#container)
- [id](app.CommentEditForm.md#id)
- [message](app.CommentEditForm.md#message)

### Methods

- [clearForm](app.CommentEditForm.md#clearform)
- [init](app.CommentEditForm.md#init)
- [onSubmitResponse](app.CommentEditForm.md#onsubmitresponse)
- [submitForm](app.CommentEditForm.md#submitform)

## Constructors

### constructor

• **new CommentEditForm**(): [`CommentEditForm`](app.CommentEditForm.md)

Intialize the object b setting buttons to do actions
when clicked

#### Returns

[`CommentEditForm`](app.CommentEditForm.md)

#### Defined in

[app.ts:1228](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1228)

## Properties

### container

• **container**: `HTMLElement`

The HTML element for the container of the entire module

#### Defined in

[app.ts:1222](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1222)

___

### id

• **id**: `HTMLInputElement`

The HTML element for comment id

#### Defined in

[app.ts:1217](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1217)

___

### message

• **message**: `HTMLInputElement`

The HTML element for comment message

#### Defined in

[app.ts:1212](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1212)

## Methods

### clearForm

▸ **clearForm**(): `void`

Clear the form's input fields

#### Returns

`void`

#### Defined in

[app.ts:1271](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1271)

___

### init

▸ **init**(`data`): `void`

Intialize the object b setting buttons to do actions
when clicked

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `data` | `any` | comment JSON |

#### Returns

`void`

#### Defined in

[app.ts:1247](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1247)

___

### onSubmitResponse

▸ **onSubmitResponse**(`data`): `void`

onSubmitResponse runs when the AJAX call in submitForm() returns a
result.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `data` | `any` | The object returned by the server |

#### Returns

`void`

#### Defined in

[app.ts:1337](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1337)

___

### submitForm

▸ **submitForm**(): `void`

Check if the input fields are both valid, and if so, do an AJAX call.

#### Returns

`void`

#### Defined in

[app.ts:1283](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1283)
