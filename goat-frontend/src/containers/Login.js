import React, { Component } from 'react'
import { connect } from 'react-redux'
import { Link, withRouter } from 'react-router-dom'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';

import { Error, LoginHeader, Submit } from '../components'
import { username, password } from '../utils/validation'
import { login } from '../store/actions/auth'

class Login extends Component {
    render() {
        const initialValues = {
            username: '',
            password: ''
        }

        const loginSchema = Yup.object().shape({
            username: username,
            password: password
        })

        return (
            <div className="container">
                <LoginHeader text="Sign In" />
                <div className="center">
                    <Formik
                        initialValues={initialValues}
                        validationSchema={loginSchema}
                        onSubmit={this.onSubmit}
                    >
                        {({ errors, touched }) => (
                            <Form>
                                <div className="row">
                                    <div className="col-12 text-center mb-4">
                                        {errors.username && touched.username ? <Error>{errors.username}</Error> : null}
                                        {/* <FormLabel htmlFor="username"></FormLabel> */}
                                        <Field name="username" placeholder="username"/>
                                    </div>
                                </div>

                                <div className="row">
                                    <div className="col-12 text-center mb-4">
                                        {errors.email && touched.email ? <Error>{errors.email}</Error> : null}
                                        {/* <FormLabel htmlFor="password"></FormLabel> */}
                                        <Field name="password" type="password" placeholder="password"/>
                                    </div>
                                </div>

                                <div className="row">
                                    <div className="col-12 text-center">
                                        <Submit>Log in</Submit>
                                    </div>
                                </div>
                            </Form>
                        )}
                    </Formik>
                </div>
                <div className="col-12 d-block text-center mt-4">
                    Don't have an account?
                    <Link className="text-primary-nobootstrap" to="/register"> Sign up</Link>
                </div>
            </div>
        )
    }

    onSubmit = async values => {
        try {
            await this.props.login(values)
            this.props.history.push('/')
        } catch (err) {
            console.error(err)
        }
    }
}

const mapDispatchToProps = { login }

export default connect(null, mapDispatchToProps)(withRouter(Login))