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
- [updateUserProfile](app.EditEntryForm.md#updateuserprofile)

## Constructors

### constructor

• **new EditEntryForm**(): [`EditEntryForm`](app.EditEntryForm.md)

Intialize the object b setting buttons to do actions
when clicked

#### Returns

[`EditEntryForm`](app.EditEntryForm.md)

#### Defined in

[app.ts:174](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-174)

## Methods

### clearForm

▸ **clearForm**(): `void`

Clear the form's input fields

#### Returns

`void`

#### Defined in

[app.ts:221](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-221)

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

[app.ts:191](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-191)

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

[app.ts:301](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-301)

___

### submitForm

▸ **submitForm**(): `void`

Check if the input fields are both valid, and if so, do an AJAX call.

#### Returns

`void`

#### Defined in

[app.ts:238](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-238)

___

### updateUserProfile

▸ **updateUserProfile**(`data`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `data` | [`UserProfile`](../interfaces/app.UserProfile.md) |

#### Returns

`void`

#### Defined in

[app.ts:317](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-317)
