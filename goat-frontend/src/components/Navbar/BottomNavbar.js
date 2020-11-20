import React from 'react'
import NavbarEntry from "./NavbarEntry"

const Navbar = () => {
    const pages = [
        {
            icon: "trophy",
            link: "/tournaments/my",
            name: "Tournaments"
        },
        {
            icon: "gamepad",
            link: "/games",
            name: "Games"
        },
        {
            icon: "shopping-cart",
            link: "/",
            name: "Store"
        }
    ]

    const pagesList = pages.map(({link, name, icon}, i) => (
        <NavbarEntry key={i} link={link} name={name} icon={icon} />
    ))

    return (
        <nav className="navbar navbar-expand navbar-primary bg-primary-nobootstrap fixed-bottom">
            <ul className="navbar-nav container-fluid">
                {pagesList}
            </ul>
        </nav>
    )
}

export default Navbar