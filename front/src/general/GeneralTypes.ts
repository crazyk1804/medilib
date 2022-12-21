import {ReactNode} from "react";
import {Credential} from "../context/global/TypeDefs";

export interface ACTION {
	type: string
	payload: any
	meta?: any
	error?: boolean
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
	loginUser?: LoginUser,
	login: (credential: Credential) => void

	alertOpen: boolean,
	alert: (title: string, message: string) => void
	alertTitle: string
	alertMessage: string
	alertClose: () => void;
}

