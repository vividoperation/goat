//import React from 'react';
//import { render } from 'react-dom';
//import { TestGameFront } from './containers';

import $ from 'jquery'; // eslint-disable-line no-unused-vars
import Popper from 'popper.js'; // eslint-disable-line no-unused-vars

//import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
//
//render(
////    <TestGameFront />,
//    <Sample />,
//  document.getElementById('root')
//)


import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import './bootstrapOverrides.css'
import TestGameFront from './TestGameFront';
import * as serviceWorker from './serviceWorker';

ReactDOM.render(<TestGameFront />, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();