import {ReactNode} from "react";

export interface ACTION {
	type: string
	payload: any
	meta: any
	error: boolean
}

export interface LoginUser {
	id: string,
	authorities: string[]
	hasRole: (role: string) => boolean
}

export interface ReactParent {
	children?: ReactNode
}

export interface GlobalContextState {
	loginUser?: LoginUser
}

