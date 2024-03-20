[web-bridge-tutorial](../README.md) / [Modules](../modules.md) / [app](../modules/app.md) / EditEntryForm

# Class: EditEntryForm

[app](../modules/app.md).EditEntryForm

EditEntryForm contains all code for editing an entry

## Table of contents

### Constructors

- [constructor](app.EditEntryForm.md#constructor)

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

[app.ts:133](https://bitbucket.org/sml3/cse216_sp24_team_21/src/ea4b1da/web/app.ts#lines-133)

## Methods

### clearForm

▸ **clearForm**(): `void`

Clear the form's input fields

#### Returns

`void`

#### Defined in

[app.ts:170](https://bitbucket.org/sml3/cse216_sp24_team_21/src/ea4b1da/web/app.ts#lines-170)

___

### init

▸ **init**(`data`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `data` | `any` |

#### Returns

`void`

#### Defined in

[app.ts:141](https://bitbucket.org/sml3/cse216_sp24_team_21/src/ea4b1da/web/app.ts#lines-141)

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

[app.ts:249](https://bitbucket.org/sml3/cse216_sp24_team_21/src/ea4b1da/web/app.ts#lines-249)

___

### submitForm

▸ **submitForm**(): `void`

Check if the input fields are both valid, and if so, do an AJAX call.

#### Returns

`void`

#### Defined in

[app.ts:186](https://bitbucket.org/sml3/cse216_sp24_team_21/src/ea4b1da/web/app.ts#lines-186)
