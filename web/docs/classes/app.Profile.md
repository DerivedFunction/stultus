[web-bridge-tutorial](../README.md) / [Modules](../modules.md) / [app](../modules/app.md) / Profile

# Class: Profile

[app](../modules/app.md).Profile

Profile is all the code to edit and view a current user's profile

## Table of contents

### Constructors

- [constructor](app.Profile.md#constructor)

### Properties

- [container](app.Profile.md#container)
- [genderMenu](app.Profile.md#gendermenu)
- [nameBox](app.Profile.md#namebox)
- [noteBox](app.Profile.md#notebox)
- [soMenu](app.Profile.md#somenu)

### Methods

- [clearForm](app.Profile.md#clearform)
- [fillForm](app.Profile.md#fillform)
- [init](app.Profile.md#init)
- [onSubmitResponse](app.Profile.md#onsubmitresponse)
- [submitForm](app.Profile.md#submitform)

## Constructors

### constructor

• **new Profile**(): [`Profile`](app.Profile.md)

#### Returns

[`Profile`](app.Profile.md)

#### Defined in

[app.ts:771](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-771)

## Properties

### container

• **container**: `HTMLElement`

The HTML element for the container of the entire module

#### Defined in

[app.ts:770](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-770)

___

### genderMenu

• **genderMenu**: `HTMLSelectElement`

The HTML element for gender

#### Defined in

[app.ts:755](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-755)

___

### nameBox

• **nameBox**: `HTMLInputElement`

The HTML element for username

#### Defined in

[app.ts:750](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-750)

___

### noteBox

• **noteBox**: `HTMLTextAreaElement`

The HTML element for user note

#### Defined in

[app.ts:765](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-765)

___

### soMenu

• **soMenu**: `HTMLSelectElement`

The HTML element for SO

#### Defined in

[app.ts:760](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-760)

## Methods

### clearForm

▸ **clearForm**(): `void`

Clears the form

#### Returns

`void`

#### Defined in

[app.ts:865](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-865)

___

### fillForm

▸ **fillForm**(`data`): `void`

Fill out the profile form with user information
when clicked

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `data` | `any` | the UserData JSON |

#### Returns

`void`

#### Defined in

[app.ts:809](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-809)

___

### init

▸ **init**(): `Promise`\<`void`\>

Intialize the object b setting buttons to do actions
when clicked

#### Returns

`Promise`\<`void`\>

#### Defined in

[app.ts:784](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-784)

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

[app.ts:913](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-913)

___

### submitForm

▸ **submitForm**(): `void`

Submit the form to update user profile

#### Returns

`void`

#### Defined in

[app.ts:873](https://bitbucket.org/sml3/cse216_sp24_team_21/src/da9d4b71a0cdbaa79f676d5395242a23f3c049fb/web/app.ts#lines-873)
