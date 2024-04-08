[web-bridge-tutorial](../README.md) / [Modules](../modules.md) / app

# Module: app

## Table of contents

### Classes

- [EditEntryForm](../classes/app.EditEntryForm.md)
- [ElementList](../classes/app.ElementList.md)
- [NewEntryForm](../classes/app.NewEntryForm.md)

### Interfaces

- [UserProfile](../interfaces/app.UserProfile.md)

### Variables

- [$](app.md#$)
- [backendUrl](app.md#backendurl)
- [componentName](app.md#componentname)
- [editEntryForm](app.md#editentryform)
- [mainList](app.md#mainlist)
- [newEntryForm](app.md#newentryform)

### Functions

- [InvalidContentMsg](app.md#invalidcontentmsg)

## Variables

### $

• **$**: `any`

Prevent compiler errors when using jQuery by test
setting $ to any

#### Defined in

[app.ts:5](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-5)

___

### backendUrl

• `Const` **backendUrl**: ``"localhost:8998"``

Backend server link to dokku

#### Defined in

[app.ts:16](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-16)

___

### componentName

• `Const` **componentName**: ``"messages"``

Component name to fetch resources

#### Defined in

[app.ts:22](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-22)

___

### editEntryForm

• **editEntryForm**: [`EditEntryForm`](../classes/app.EditEntryForm.md)

Global variable to be referenced for ElementList

#### Defined in

[app.ts:162](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-162)

___

### mainList

• **mainList**: [`ElementList`](../classes/app.ElementList.md)

Global variable to be referenced for ElementList

#### Defined in

[app.ts:339](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-339)

___

### newEntryForm

• **newEntryForm**: [`NewEntryForm`](../classes/app.NewEntryForm.md)

Global variable to be referenced for newEntryForm

#### Defined in

[app.ts:10](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-10)

## Functions

### InvalidContentMsg

▸ **InvalidContentMsg**(): `void`

Unhide error message from HTML index for a moment

#### Returns

`void`

#### Defined in

[app.ts:758](https://bitbucket.org/sml3/cse216_sp24_team_21/src/f7eaa97199e35a778ae71827ae32941978a60f29/web/app.ts#lines-758)
