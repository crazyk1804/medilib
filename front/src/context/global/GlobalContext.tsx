import React, {createContext, ReactNode, useReducer} from 'react';
import {GlobalContextReducer, initialGlobalContextState} from "./GlobalContextReducer";
import {ReactParent} from "../../general/GeneralTypes";
import {doFetch} from "../../util/web-util";
import {Credential} from "./TypeDefs";

export const GlobalContext = createContext(initialGlobalContextState);

export const GlobalContextProvider = ({children}: ReactParent) => {
	const [value, dispatch] = useReducer(GlobalContextReducer, initialGlobalContextState);

	const login = async (credential: Credential) => {
		const token = await doFetch({
			url: '/authenticate',
			data: credential,
		});
		console.log(token);
	}

	return (
		<GlobalContext.Provider value={{
			...value,
			login
		}}>
			{children}
		</GlobalContext.Provider>
	)
}