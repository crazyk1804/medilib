import React, {createContext, ReactNode} from 'react';
import {initialGlobalContextState} from "./GlobalContextReducer";
import {ReactParent} from "../../general/GeneralTypes";

const GlobalContext = createContext(initialGlobalContextState);

export const GlobalContextProvider = ({children}: ReactParent) => {
	return (
		<GlobalContext.Provider value={{
			...initialGlobalContextState
		}}>
			{children}
		</GlobalContext.Provider>
	)
}