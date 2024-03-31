[web-bridge-tutorial](../README.md) / [Modules](../modules.md) / [app](../modules/app.md) / ElementList

# Class: ElementList

[app](../modules/app.md).ElementList

ElementList provides a way to see the data stored in server

## Table of contents

### Constructors

- [constructor](app.ElementList.md#constructor)

### Methods

- [buttons](app.ElementList.md#buttons)
- [clickDelete](app.ElementList.md#clickdelete)
- [clickEdit](app.ElementList.md#clickedit)
- [clickLike](app.ElementList.md#clicklike)
- [refresh](app.ElementList.md#refresh)
- [update](app.ElementList.md#update)

## Constructors

### constructor

• **new ElementList**(): [`ElementList`](app.ElementList.md)

#### Returns

[`ElementList`](app.ElementList.md)

## Methods

### buttons

▸ **buttons**(`id`): `DocumentFragment`

Adds a delete, edit button to the HTML for each row

#### Parameters

| Name | Type |
| :------ | :------ |
| `id` | `string` |

#### Returns

`DocumentFragment`

a new button

#### Defined in

[app.ts:401](https://bitbucket.org/sml3/cse216_sp24_team_21/src/504518a/web/app.ts#lines-401)

___

### clickDelete

▸ **clickDelete**(`e`): `void`

Delete the item off the table

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `e` | `Event` | to be deleted |

#### Returns

`void`

#### Defined in

[app.ts:437](https://bitbucket.org/sml3/cse216_sp24_team_21/src/504518a/web/app.ts#lines-437)

___

### clickEdit

▸ **clickEdit**(`e`): `void`

clickEdit is the code we run in response to a click of a delete button

#### Parameters

| Name | Type |
| :------ | :------ |
| `e` | `Event` |

#### Returns

`void`

#### Defined in

[app.ts:515](https://bitbucket.org/sml3/cse216_sp24_team_21/src/504518a/web/app.ts#lines-515)

___

### clickLike

▸ **clickLike**(`e`): `void`

Ajax function that sends HTTP function to update like count

#### Parameters

| Name | Type |
| :------ | :------ |
| `e` | `Event` |

#### Returns

`void`

#### Defined in

[app.ts:476](https://bitbucket.org/sml3/cse216_sp24_team_21/src/504518a/web/app.ts#lines-476)

___

### refresh

▸ **refresh**(): `void`

Refresh updates the messageList
@

#### Returns

`void`

#### Defined in

[app.ts:306](https://bitbucket.org/sml3/cse216_sp24_team_21/src/504518a/web/app.ts#lines-306)

___

### update

▸ **update**(`data`): `void`

Update the data

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `data` | `any` | to be updated |

#### Returns

`void`

#### Defined in

[app.ts:341](https://bitbucket.org/sml3/cse216_sp24_team_21/src/504518a/web/app.ts#lines-341)
