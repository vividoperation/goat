import React, { Component } from 'react'
import { connect } from 'react-redux'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';

import { Error, FormLabel, Logout, Submit, LoginHeader } from '../components'
import { email, phone } from '../utils/validation'
import { updateUser, getUser } from '../store/actions/user'
import { logout } from '../store/actions/auth'

class Profile extends Component {
    constructor(props) {
        super(props)
        props.getUser()
    }

    render() {
        const logoutButton = this.props.user ? <Logout onLogout={this.props.logout} /> : null

        const updateProfileSchema = Yup.object().shape({
            email: email,
            phone: phone
        })

        return (
            <div className="container">
                {logoutButton}
                 <LoginHeader text="Profile"/>
                 <div className="center">
                <Formik
                    initialValues={this.props.user}
                    validationSchema={updateProfileSchema}
                    onSubmit={this.onSubmit}
                >
                    {({ errors, touched }) => (
                        <Form>
                            <div className="row">
                                <div className="col-12 text-center mb-4">
                                    {errors.email && touched.email ? <Error>{errors.email}</Error> : null}
                                    <FormLabel htmlFor="email"></FormLabel>
                                    <Field name="email" type="email" placeholder="email"/>
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
                                <div className="col-12 text-center">
                                    <Submit>Update Profile</Submit>
                                </div>
                            </div>
                        </Form>
                    )}
                </Formik>
                </div>
            </div>
        )
    }

    onSubmit = async values => {
        try {
            await this.props.updateUser(values)
        } catch (err) {
            console.error(err)
        }
    }
}

const mapStateToProps = ({user}) => ({
    user
})

const mapDispatchToProps = { updateUser, getUser, logout }

export default connect(mapStateToProps, mapDispatchToProps)(Profile)