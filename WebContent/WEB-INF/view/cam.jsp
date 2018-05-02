<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    

  <link type="text/css"
    rel = "stylesheet"
    href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css"
  />
  
<script src='${pageContext.request.contextPath}/resources/js/sldp-v2.3.2.min.js' type='text/javascript'></script>

<div class="row">
  <div class="col-xs-4">
    <div id='sldp_player_wrapper'></div>
    <h4>Local</h4>
  </div>
  <div class="col-xs-4">
    <div id='sldp_player_wrapper2'></div>
    <h4>Lane 2</h4>
  </div>
    <div class="col-xs-4">
    <div id='sldp_player_wrapper3'></div>
    <h4>Lane 3</h4>
  </div>
</div>

<div class="row">
  <div class="col-xs-4">
    <div id='sldp_player_wrapper4'></div>
    <h4>Lane 4</h4>
  </div>
    <div class="col-xs-4">
    <div id='sldp_player_wrapper5'></div>
    <h4>Lane 5</h4>
  </div>
  <div class="col-xs-4">
    <div id='sldp_player_wrapper6'></div>
    <h4>Lane 6</h4>
  </div>
</div>

<div class="row">
  <div class="col-xs-4">
    <div id='sldp_player_wrapper7'></div>
    <h4>Lane 7</h4>
  </div>
</div>

<script>
  document.addEventListener('DOMContentLoaded', initPlayer);
  var sldpPlayer;
  function initPlayer(){
    sldpPlayer = SLDP.init({
      container: 'sldp_player_wrapper',
      stream_url: "${address}",
      height: 200,
      width: 320,
      autoplay: true
    });
  
  };
  
  function removePlayer(){
    sldpPlayer.destroy()
  }
</script>

<script>
  document.addEventListener('DOMContentLoaded', initPlayer);
  var sldpPlayer;
  function initPlayer(){
    sldpPlayer = SLDP.init({
      container: 'sldp_player_wrapper2',
      stream_url: "ws://192.168.0.2:8081/cam/Lane2",
      height: 200,
      width: 320,
      autoplay: true
    });
  
  };
  
  function removePlayer(){
    sldpPlayer.destroy()
  }
</script>

<script>
  document.addEventListener('DOMContentLoaded', initPlayer);
  var sldpPlayer;
  function initPlayer(){
    sldpPlayer = SLDP.init({
      container: 'sldp_player_wrapper3',
      stream_url: "ws://192.168.0.2:8081/cam/Lane3",
      height: 200,
      width: 320,
      autoplay: true
    });
  
  };
  
  function removePlayer(){
    sldpPlayer.destroy()
  }
</script>

<script>
  document.addEventListener('DOMContentLoaded', initPlayer);
  var sldpPlayer;
  function initPlayer(){
    sldpPlayer = SLDP.init({
      container: 'sldp_player_wrapper4',
      stream_url: "ws://192.168.0.2:8081/cam/L4",
      height: 200,
      width: 320,
      autoplay: true
    });
  
  };
  
  function removePlayer(){
    sldpPlayer.destroy()
  }
</script>

<script>
  document.addEventListener('DOMContentLoaded', initPlayer);
  var sldpPlayer;
  function initPlayer(){
    sldpPlayer = SLDP.init({
      container: 'sldp_player_wrapper5',
      stream_url: "ws://192.168.0.2:8081/cam/L5",
      height: 200,
      width: 320,
      autoplay: true
    });
  
  };
  
  function removePlayer(){
    sldpPlayer.destroy()
  }
</script>

<script>
  document.addEventListener('DOMContentLoaded', initPlayer);
  var sldpPlayer;
  function initPlayer(){
    sldpPlayer = SLDP.init({
      container: 'sldp_player_wrapper6',
      stream_url: "ws://192.168.0.2:8081/cam/L6",
      height: 200,
      width: 320,
      autoplay: true
    });
  
  };
  
  function removePlayer(){
    sldpPlayer.destroy()
  }
</script>

<script>
  document.addEventListener('DOMContentLoaded', initPlayer);
  var sldpPlayer;
  function initPlayer(){
    sldpPlayer = SLDP.init({
      container: 'sldp_player_wrapper7',
      stream_url: "ws://192.168.0.2:8081/cam/L7",
      height: 200,
      width: 320,
      autoplay: true
    });
  
  };
  
  function removePlayer(){
    sldpPlayer.destroy()
  }
</script>

