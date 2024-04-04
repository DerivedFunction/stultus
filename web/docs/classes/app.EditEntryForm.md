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

[app.ts:150](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f28616e/web/app.ts#lines-150)

## Methods

### clearForm

▸ **clearForm**(): `void`

Clear the form's input fields

#### Returns

`void`

#### Defined in

[app.ts:197](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f28616e/web/app.ts#lines-197)

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

[app.ts:167](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f28616e/web/app.ts#lines-167)

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

[app.ts:277](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f28616e/web/app.ts#lines-277)

___

### submitForm

▸ **submitForm**(): `void`

Check if the input fields are both valid, and if so, do an AJAX call.

#### Returns

`void`

#### Defined in

[app.ts:214](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f28616e/web/app.ts#lines-214)
