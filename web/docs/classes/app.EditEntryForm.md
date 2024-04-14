[web-bridge-tutorial](../README.md) / [Modules](../modules.md) / [app](../modules/app.md) / EditEntryForm

# Class: EditEntryForm

[app](../modules/app.md).EditEntryForm

EditEntryForm contains all code for editing an entry

## Table of contents

### Constructors

- [constructor](app.EditEntryForm.md#constructor)

### Properties

- [container](app.EditEntryForm.md#container)
- [id](app.EditEntryForm.md#id)
- [message](app.EditEntryForm.md#message)
- [title](app.EditEntryForm.md#title)

### Methods

- [clearForm](app.EditEntryForm.md#clearform)
- [init](app.EditEntryForm.md#init)
- [onSubmitResponse](app.EditEntryForm.md#onsubmitresponse)
- [submitForm](app.EditEntryForm.md#submitform)

## Constructors

### constructor

• **new EditEntryForm**(): [`EditEntryForm`](app.EditEntryForm.md)

Intialize the object b setting buttons to do actions
when clicked

#### Returns

[`EditEntryForm`](app.EditEntryForm.md)

#### Defined in

[app.ts:188](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-188)

## Properties

### container

• **container**: `HTMLElement`

The HTML element for the container of the entire module

#### Defined in

[app.ts:182](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-182)

___

### id

• **id**: `HTMLInputElement`

The HTML element for id

#### Defined in

[app.ts:177](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-177)

___

### message

• **message**: `HTMLInputElement`

The HTML element for message

#### Defined in

[app.ts:172](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-172)

___

### title

• **title**: `HTMLInputElement`

The HTML element for title

#### Defined in

[app.ts:167](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-167)

## Methods

### clearForm

▸ **clearForm**(): `void`

Clear the form's input fields

#### Returns

`void`

#### Defined in

[app.ts:228](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-228)

___

### init

▸ **init**(`data`): `void`

Intialize the object b setting buttons to do actions
when clicked

#### Parameters

| Name | Type |
| :------ | :------ |
| `data` | `any` |

#### Returns

`void`

#### Defined in

[app.ts:203](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-203)

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

[app.ts:298](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-298)

___

### submitForm

▸ **submitForm**(): `void`

Check if the input fields are both valid, and if so, do an AJAX call.

#### Returns

`void`

#### Defined in

[app.ts:241](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-241)
