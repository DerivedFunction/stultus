[web-bridge-tutorial](../README.md) / [Modules](../modules.md) / [app](../modules/app.md) / NewEntryForm

# Class: NewEntryForm

[app](../modules/app.md).NewEntryForm

NewEntryForm has all the code for the form for adding an entry

## Table of contents

### Constructors

- [constructor](app.NewEntryForm.md#constructor)

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

[app.ts:34](https://bitbucket.org/sml3/cse216_sp24_team_21/src/504518a/web/app.ts#lines-34)

## Methods

### clearForm

▸ **clearForm**(): `void`

Clear the input fields

#### Returns

`void`

#### Defined in

[app.ts:47](https://bitbucket.org/sml3/cse216_sp24_team_21/src/504518a/web/app.ts#lines-47)

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

[app.ts:121](https://bitbucket.org/sml3/cse216_sp24_team_21/src/504518a/web/app.ts#lines-121)

___

### submitForm

▸ **submitForm**(): `void`

Check if input is valid before submitting with AJAX call

#### Returns

`void`

#### Defined in

[app.ts:62](https://bitbucket.org/sml3/cse216_sp24_team_21/src/504518a/web/app.ts#lines-62)
