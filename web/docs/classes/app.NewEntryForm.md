[web-bridge-tutorial](../README.md) / [Modules](../modules.md) / [app](../modules/app.md) / NewEntryForm

# Class: NewEntryForm

[app](../modules/app.md).NewEntryForm

NewEntryForm has all the code for the form for adding an entry

## Table of contents

### Constructors

- [constructor](app.NewEntryForm.md#constructor)

### Properties

- [container](app.NewEntryForm.md#container)
- [message](app.NewEntryForm.md#message)
- [title](app.NewEntryForm.md#title)

### Methods

- [clearForm](app.NewEntryForm.md#clearform)
- [onSubmitResponse](app.NewEntryForm.md#onsubmitresponse)
- [submitForm](app.NewEntryForm.md#submitform)

## Constructors

### constructor

• **new NewEntryForm**(): [`NewEntryForm`](app.NewEntryForm.md)

Intialize the object  setting buttons to do actions when clicked

#### Returns

[`NewEntryForm`](app.NewEntryForm.md)

#### Defined in

[app.ts:59](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-59)

## Properties

### container

• **container**: `HTMLElement`

The HTML element for the container of the entire module

#### Defined in

[app.ts:54](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-54)

___

### message

• **message**: `HTMLInputElement`

The HTML element for message

#### Defined in

[app.ts:49](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-49)

___

### title

• **title**: `HTMLInputElement`

The HTML element for title

#### Defined in

[app.ts:44](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-44)

## Methods

### clearForm

▸ **clearForm**(): `void`

Clear the input fields

#### Returns

`void`

#### Defined in

[app.ts:72](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-72)

___

### onSubmitResponse

▸ **onSubmitResponse**(`data`): `void`

Runs when AJAX call in submitForm() returns a result

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `data` | `any` | obj returned by server |

#### Returns

`void`

#### Defined in

[app.ts:136](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-136)

___

### submitForm

▸ **submitForm**(): `void`

Check if input is valid before submitting with AJAX call

#### Returns

`void`

#### Defined in

[app.ts:84](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-84)
