import React, { Component } from 'react'
import { connect } from 'react-redux'
import { Link, withRouter } from 'react-router-dom'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';

import { Error, FormLabel, LoginHeader, Submit } from '../components'
import { email, phone, username, password } from '../utils/validation'
import { register } from '../store/actions/auth'

class Register extends Component {
    render() {
        const initialValues = {
            email: '',
            phone: '',
            username: '',
            password: ''
        }

        const registerSchema = Yup.object().shape({
            email: email,
            phone: phone,
            username: username,
            password: password
        })

        return (
            <div className="container">
                <LoginHeader text="Sign Up" />
                <div className="center">
                    <Formik
                        initialValues={initialValues}
                        validationSchema={registerSchema}
                        onSubmit={this.onSubmit}
                    >
                        {({ errors, touched }) => (
                            <Form>
                                <div className="row">
                                    <div className="col-12 text-center mb-4">
                                        {errors.email && touched.email ? <Error>{errors.email}</Error> : null}
                                        <FormLabel htmlFor="email"></FormLabel>
                                        <Field name="email" type="email" placeholder="email" />
                                    </div>
                                </div>

                                <div className="row">
                                    <div className="col-12 text-center mb-4">
                                        {errors.phone && touched.phone ? <Error>{errors.phone}</Error> : null}
                                        <FormLabel htmlFor="phone"></FormLabel>
                                        <Field name="phone" placeholder="phone"/>
                                    </div>
                                </div>

                                <div className="row">
                                    <div className="col-12 text-center mb-4">
                                        {errors.username && touched.username ? <Error>{errors.username}</Error> : null}
                                        <FormLabel htmlFor="username"></FormLabel>
                                        <Field name="username" placeholder="username"/>
                                    </div>
                                </div>

                                <div className="row">
                                    <div className="col-12 text-center mb-4">
                                    {errors.password && touched.password ? <Error>{errors.password}</Error> : null}
                                    <FormLabel htmlFor="password"></FormLabel>
                                    <Field name="password" type="password" placeholder="password"/>
                                    </div>
                                </div>

                                <div className="row">
                                    <div className="col-12 text-center">
                                        <Submit>Sign up</Submit>
                                    </div>
                                </div>
                            </Form>
                        )}
                    </Formik>
                </div>
                <div className="col-12 d-block text-center mt-4">
                    Already have an account?
                    <Link className="text-primary-nobootstrap" to="/login"> Sign in</Link>
                </div>
            </div>
        )
    }

    onSubmit = async values => {
        try {
            await this.props.register(values)
            this.props.history.push('/')
        } catch (err) {
            console.error(err)
        }
    }
}

const mapDispatchToProps = { register }

export default connect(null, mapDispatchToProps)(withRouter(Register))