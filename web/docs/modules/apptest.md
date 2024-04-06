[web-bridge-tutorial](../README.md) / [Modules](../modules.md) / apptest

# Module: apptest

## Table of contents

### Variables

- [afterAll](apptest.md#afterall)
- [afterEach](apptest.md#aftereach)
- [beforeAll](apptest.md#beforeall)
- [beforeEach](apptest.md#beforeeach)
- [delay](apptest.md#delay)
- [describe](apptest.md#describe)
- [expect](apptest.md#expect)
- [it](apptest.md#it)
- [originalMsg](apptest.md#originalmsg)
- [originalTitle](apptest.md#originaltitle)

### Functions

- [clickEditBtn](apptest.md#clickeditbtn)
- [getOriginal](apptest.md#getoriginal)
- [wait](apptest.md#wait)

## Variables

### afterAll

• **afterAll**: `any`

Executes after all the tests are complete that resets the system

#### Defined in

[apptest.ts:36](https://bitbucket.org/sml3/cse216_sp24_team_21/src/bd184c8/web/apptest.ts#lines-36)

___

### afterEach

• **afterEach**: `any`

Waiting after executing each test

#### Defined in

[apptest.ts:42](https://bitbucket.org/sml3/cse216_sp24_team_21/src/bd184c8/web/apptest.ts#lines-42)

___

### beforeAll

• **beforeAll**: `any`

Initializes values

#### Defined in

[apptest.ts:30](https://bitbucket.org/sml3/cse216_sp24_team_21/src/bd184c8/web/apptest.ts#lines-30)

___

### beforeEach

• **beforeEach**: `any`

Allows backend to sync

#### Defined in

[apptest.ts:24](https://bitbucket.org/sml3/cse216_sp24_team_21/src/bd184c8/web/apptest.ts#lines-24)

___

### delay

• `Const` **delay**: ``1000``

Delay of one second to be used to make the tests viewable

#### Defined in

[apptest.ts:49](https://bitbucket.org/sml3/cse216_sp24_team_21/src/bd184c8/web/apptest.ts#lines-49)

___

### describe

• **describe**: `any`

Global vars

#### Defined in

[apptest.ts:6](https://bitbucket.org/sml3/cse216_sp24_team_21/src/bd184c8/web/apptest.ts#lines-6)

___

### expect

• **expect**: `any`

Global vars

#### Defined in

[apptest.ts:18](https://bitbucket.org/sml3/cse216_sp24_team_21/src/bd184c8/web/apptest.ts#lines-18)

___

### it

• **it**: `any`

Testis Functionality

#### Defined in

[apptest.ts:12](https://bitbucket.org/sml3/cse216_sp24_team_21/src/bd184c8/web/apptest.ts#lines-12)

___

### originalMsg

• **originalMsg**: `any`

Backup for original message element

#### Defined in

[apptest.ts:60](https://bitbucket.org/sml3/cse216_sp24_team_21/src/bd184c8/web/apptest.ts#lines-60)

___

### originalTitle

• **originalTitle**: `any`

Backup for original title element

#### Defined in

[apptest.ts:55](https://bitbucket.org/sml3/cse216_sp24_team_21/src/bd184c8/web/apptest.ts#lines-55)

## Functions

### clickEditBtn

▸ **clickEditBtn**(): `void`

TEST FUNCTION: 
CLICK EDIT BUTTON AND WAIT FOR BACKEND

#### Returns

`void`

#### Defined in

[apptest.ts:407](https://bitbucket.org/sml3/cse216_sp24_team_21/src/bd184c8/web/apptest.ts#lines-407)

___

### getOriginal

▸ **getOriginal**(): `void`

TEST FUNCTION: 
GET THE ORIGINAL MESSAGE AND TITLE STRING

#### Returns

`void`

#### Defined in

[apptest.ts:384](https://bitbucket.org/sml3/cse216_sp24_team_21/src/bd184c8/web/apptest.ts#lines-384)

___

### wait

▸ **wait**(`done`): `void`

TEST FUNCTION: 
PAUSE PROGRAM FOR 1 SECOND TO BE VIEWED BY USER/TESTER

#### Parameters

| Name | Type |
| :------ | :------ |
| `done` | () => `void` |

#### Returns

`void`

#### Defined in

[apptest.ts:396](https://bitbucket.org/sml3/cse216_sp24_team_21/src/bd184c8/web/apptest.ts#lines-396)
