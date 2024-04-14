[web-bridge-tutorial](../README.md) / [Modules](../modules.md) / [app](../modules/app.md) / ViewUserForm

# Class: ViewUserForm

[app](../modules/app.md).ViewUserForm

CommentEditForm contains all code for viewing a user

## Table of contents

### Constructors

- [constructor](app.ViewUserForm.md#constructor)

### Properties

- [container](app.ViewUserForm.md#container)
- [email](app.ViewUserForm.md#email)
- [note](app.ViewUserForm.md#note)
- [username](app.ViewUserForm.md#username)

### Methods

- [clearForm](app.ViewUserForm.md#clearform)
- [init](app.ViewUserForm.md#init)

## Constructors

### constructor

• **new ViewUserForm**(): [`ViewUserForm`](app.ViewUserForm.md)

Intialize the object b setting buttons to do actions
when clicked

#### Returns

[`ViewUserForm`](app.ViewUserForm.md)

#### Defined in

[app.ts:1392](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1392)

## Properties

### container

• **container**: `HTMLElement`

The HTML element for the container of the entire module

#### Defined in

[app.ts:1386](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1386)

___

### email

• **email**: `HTMLElement`

The HTML element for email

#### Defined in

[app.ts:1381](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1381)

___

### note

• **note**: `HTMLElement`

The HTML element for note

#### Defined in

[app.ts:1376](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1376)

___

### username

• **username**: `HTMLElement`

The HTML element for username

#### Defined in

[app.ts:1371](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1371)

## Methods

### clearForm

▸ **clearForm**(): `void`

Clear the form's input fields

#### Returns

`void`

#### Defined in

[app.ts:1424](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1424)

___

### init

▸ **init**(`data`): `void`

Intialize the object b setting buttons to do actions
when clicked

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `data` | `any` | JSON of UserDataLite |

#### Returns

`void`

#### Defined in

[app.ts:1403](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-1403)
