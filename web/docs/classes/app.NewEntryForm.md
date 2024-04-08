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
- [updateUserProfile](app.NewEntryForm.md#updateuserprofile)

## Constructors

### constructor

• **new NewEntryForm**(): [`NewEntryForm`](app.NewEntryForm.md)

Intialize the object  setting buttons to do actions when clicked

#### Returns

[`NewEntryForm`](app.NewEntryForm.md)

#### Defined in

[app.ts:41](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-41)

## Methods

### clearForm

▸ **clearForm**(): `void`

Clear the input fields

#### Returns

`void`

#### Defined in

[app.ts:54](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-54)

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

[app.ts:128](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-128)

___

### submitForm

▸ **submitForm**(): `void`

Check if input is valid before submitting with AJAX call

#### Returns

`void`

#### Defined in

[app.ts:69](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-69)

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

[app.ts:140](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-140)
