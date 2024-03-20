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

[app.ts:372](https://bitbucket.org/sml3/cse216_sp24_team_21/src/1aadf3e/web/app.ts#lines-372)

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

[app.ts:408](https://bitbucket.org/sml3/cse216_sp24_team_21/src/1aadf3e/web/app.ts#lines-408)

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

[app.ts:481](https://bitbucket.org/sml3/cse216_sp24_team_21/src/1aadf3e/web/app.ts#lines-481)

___

### clickLike

▸ **clickLike**(`e`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `e` | `Event` |

#### Returns

`void`

#### Defined in

[app.ts:443](https://bitbucket.org/sml3/cse216_sp24_team_21/src/1aadf3e/web/app.ts#lines-443)

___

### refresh

▸ **refresh**(): `void`

Refresh updates the messageList

#### Returns

`void`

#### Defined in

[app.ts:277](https://bitbucket.org/sml3/cse216_sp24_team_21/src/1aadf3e/web/app.ts#lines-277)

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

[app.ts:312](https://bitbucket.org/sml3/cse216_sp24_team_21/src/1aadf3e/web/app.ts#lines-312)
