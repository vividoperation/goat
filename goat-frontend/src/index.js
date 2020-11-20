import React from 'react';
import { render } from 'react-dom';
import { MainRouter } from './containers';
import { Provider } from 'react-redux'
import { configureStore, createPersistor } from './store'
import { PersistGate } from 'redux-persist/lib/integration/react';
import { library } from '@fortawesome/fontawesome-svg-core'
import { faUsers, faHandshake, faGamepad, faTrophy, faUser, faShoppingCart } from '@fortawesome/free-solid-svg-icons'

import { Loading } from './components'

import $ from 'jquery'; // eslint-disable-line no-unused-vars
import Popper from 'popper.js'; // eslint-disable-line no-unused-vars

import './bootstrapOverrides.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';

const store = configureStore()

library.add(faGamepad, faTrophy, faUser, faShoppingCart, faUsers, faHandshake)

render(
  <Provider store={store}>
    <PersistGate loading={<Loading />} persistor={createPersistor(store)}>
      <MainRouter />
    </PersistGate>
  </Provider>,
  document.getElementById('root')
)