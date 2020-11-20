import React from 'react'
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

const NavbarEntry = ({link, name, icon}) => (
    <li className="col-4 nav-item nav-link">
        <Link to={link} className="text-white d-block text-center">
            <FontAwesomeIcon className="d-block mx-auto" icon={icon} size="lg" />
            {name}
        </Link>
    </li>
)

export default NavbarEntry