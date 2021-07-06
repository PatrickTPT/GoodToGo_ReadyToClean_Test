package com.example.newfunctest_recycle

interface Headers {

    /*fun reqID(): String {
        val hex =
            arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
        val r = Random()
        var id = ""
        for (i in 0..9) id += hex[r.nextInt(16)].toString()
        return id
    }  // verified!

    fun reqTime(): String {
        return System.currentTimeMillis().toString()
    }  // verified!

    fun standardAddOrderTime(date: Long): String {
        val d = Date()
        d.time = d.time + 259200
        var secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
        //var myKeys = "n2kuRxmJ9cTbxqmo"
        //var base64Key = Encoders.BASE64.encode(key.getEncoded())
        return Jwts.builder().setId(reqID())
            .setIssuedAt(Date())
            .setExpiration(d)
            .claim("orderTime", date)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }*/
}