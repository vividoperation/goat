import { applyMiddleware, createStore, compose } from 'redux'
import thunk from 'redux-thunk'
import { persistReducer, persistStore } from 'redux-persist'
import storage from 'redux-persist/lib/storage';
import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';

import rootReducer from './reducers'
import { logger } from './middleware'
import { monitorReducer } from './enhancers'


export const configureStore = preloadedState => {
    const middlewares = [thunk]

    if (process.env.NODE_ENV === 'development') {
        middlewares.push(logger)
    }

    const middlewareEnhancer = applyMiddleware(...middlewares)

    const enhancers = [middlewareEnhancer]

    if (process.env.NODE_ENV === 'development') {
        enhancers.push(monitorReducer)
    }

    const composedEnhancers = compose(...enhancers)

    const persistConfig = {
        storage: storage,
        key: 'reduxStorage',
        stateReconciler: autoMergeLevel2
    }

    const persistedReducer = persistReducer(persistConfig, rootReducer)
    const store = createStore(persistedReducer, preloadedState, composedEnhancers)
    return store
}

export const createPersistor = store => persistStore(store)