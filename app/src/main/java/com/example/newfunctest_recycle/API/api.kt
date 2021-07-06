package com.example.newfunctest_recycle.API

class api {
    fun generateKey(
        reqID: String,
        reqTime: Int,
    ){



    }
    /*fun standardAddOrderTime(date: Date): String {
        val d = Date()
        d.time = d.time + 259200
        return Jwts.builder().setId(reqID())
            .setIssuedAt(Date())
            .setExpiration(d)
            .claim("orderTime", date.time)
            .signWith(SignatureAlgorithm.HS256, "n2kuRxmJ9cTbxqmo")
            .compact()
    }*//*

    private fun reqID(): String? {
        val hex =
            arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
        val r = Random()
        var id = ""
        for (i in 0..9) id += hex[r.nextInt(16)].toString()
        return id
    }

    fun reqTime(): String? {
        return System.currentTimeMillis().toString()
    }*/

}