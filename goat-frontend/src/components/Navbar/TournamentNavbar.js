import React from 'react'
import NavbarEntry from "./NavbarEntry"

const Navbar = () => {
    const pages = [
        {
            icon: "users",
            link: "/tournaments/create",
            name: "Create"
        },
        {
            icon: "user",
            link: "/tournaments/my",
            name: "My"
        },
        {
            icon: "handshake",
            link: "/tournaments/join",
            name: "Join"
        }
    ]

    const pagesList = pages.map(({link, name, icon}, i) => (
        <NavbarEntry key={i} link={link} name={name} icon={icon} />
    ))

    return (
        <nav className="navbar navbar-expand fixed-top border-bottom border-white">
            <ul className="navbar-nav container-fluid">
                {pagesList}
            </ul>
        </nav>
    )
}

export default Navbar